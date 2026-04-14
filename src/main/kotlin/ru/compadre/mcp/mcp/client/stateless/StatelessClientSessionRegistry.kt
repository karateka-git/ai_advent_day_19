package ru.compadre.mcp.mcp.client.stateless

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.sse.SSE
import io.modelcontextprotocol.kotlin.sdk.ExperimentalMcpApi
import io.modelcontextprotocol.kotlin.sdk.client.Client
import io.modelcontextprotocol.kotlin.sdk.client.ClientOptions
import io.modelcontextprotocol.kotlin.sdk.client.mcpClient
import io.modelcontextprotocol.kotlin.sdk.client.mcpStreamableHttpTransport
import io.modelcontextprotocol.kotlin.sdk.types.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.types.Implementation
import io.modelcontextprotocol.kotlin.sdk.types.TextContent
import io.modelcontextprotocol.kotlin.sdk.types.Tool
import ru.compadre.mcp.mcp.client.common.model.McpConnectionSnapshot
import ru.compadre.mcp.mcp.client.common.model.McpServerInfo
import ru.compadre.mcp.mcp.client.common.model.McpToolDescriptor
import ru.compadre.mcp.mcp.client.common.toolcall.model.McpToolCallRequest
import ru.compadre.mcp.mcp.client.common.toolcall.model.McpToolCallResult
import java.util.concurrent.ConcurrentHashMap

internal interface StatelessClientSession {
    val endpoint: String

    suspend fun snapshot(): McpConnectionSnapshot

    suspend fun callTool(request: McpToolCallRequest): McpToolCallResult

    suspend fun close()
}

internal interface StatelessClientSessionRegistry {
    suspend fun getOrCreate(endpoint: String): StatelessClientSession

    suspend fun closeAll()
}

internal class DefaultStatelessClientSessionRegistry(
    private val sessionFactory: suspend (String) -> StatelessClientSession = { endpoint ->
        SdkStatelessClientSession.create(endpoint)
    },
) : StatelessClientSessionRegistry {
    private val sessions = ConcurrentHashMap<String, StatelessClientSession>()

    override suspend fun getOrCreate(endpoint: String): StatelessClientSession {
        sessions[endpoint]?.let { existing ->
            return existing
        }

        val created = sessionFactory(endpoint)
        val existing = sessions.putIfAbsent(endpoint, created)
        if (existing != null) {
            created.close()
            return existing
        }

        return created
    }

    override suspend fun closeAll() {
        val currentSessions = sessions.values.toList()
        sessions.clear()
        currentSessions.forEach { session ->
            session.close()
        }
    }
}

internal class SdkStatelessClientSession(
    override val endpoint: String,
    private val httpClient: HttpClient,
    private val sdkClient: Client,
) : StatelessClientSession {
    override suspend fun snapshot(): McpConnectionSnapshot = McpConnectionSnapshot(
        endpoint = endpoint,
        serverInfo = toServerInfo(sdkClient),
        tools = sdkClient.listTools().tools.map(::toToolDescriptor),
    )

    override suspend fun callTool(request: McpToolCallRequest): McpToolCallResult = toToolCallResult(
        toolName = request.toolName,
        result = sdkClient.callTool(
            name = request.toolName,
            arguments = request.arguments,
        ),
    )

    override suspend fun close() {
        try {
            sdkClient.close()
        } finally {
            httpClient.close()
        }
    }

    internal companion object {
        suspend fun create(endpoint: String): SdkStatelessClientSession {
            val httpClient = defaultStatelessHttpClient()
            return try {
                val sdkClient = createDefaultStatelessSdkClient(httpClient, endpoint)
                SdkStatelessClientSession(
                    endpoint = endpoint,
                    httpClient = httpClient,
                    sdkClient = sdkClient,
                )
            } catch (error: Throwable) {
                httpClient.close()
                throw error
            }
        }
    }
}

internal fun defaultStatelessHttpClient(): HttpClient = HttpClient(CIO) {
    install(SSE)
}

@OptIn(ExperimentalMcpApi::class)
internal suspend fun createDefaultStatelessSdkClient(httpClient: HttpClient, endpoint: String): Client {
    val transport = httpClient.mcpStreamableHttpTransport(endpoint)

    return mcpClient(
        clientInfo = Implementation(
            name = "local_mcp_client",
            version = "0.1.0",
            title = "Local MCP Client",
        ),
        clientOptions = ClientOptions(),
        transport = transport,
    )
}

internal fun toServerInfo(client: Client): McpServerInfo = McpServerInfo(
    name = client.serverVersion?.name ?: "<unknown>",
    version = client.serverVersion?.version ?: "<unknown>",
    title = client.serverVersion?.title,
    instructions = client.serverInstructions,
)

internal fun toToolDescriptor(tool: Tool): McpToolDescriptor = McpToolDescriptor(
    name = tool.name,
    title = tool.title,
    description = tool.description,
)

internal fun toToolCallResult(toolName: String, result: CallToolResult): McpToolCallResult = McpToolCallResult(
    toolName = toolName,
    isError = result.isError == true,
    content = result.content.map { content ->
        when (content) {
            is TextContent -> content.text
            else -> content.toString()
        }
    },
    structuredContent = result.structuredContent,
)
