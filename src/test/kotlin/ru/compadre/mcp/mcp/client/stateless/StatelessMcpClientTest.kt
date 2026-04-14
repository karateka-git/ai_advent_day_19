package ru.compadre.mcp.mcp.client.stateless

import io.modelcontextprotocol.kotlin.sdk.types.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.types.TextContent
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import ru.compadre.mcp.mcp.client.common.model.McpConnectionSnapshot
import ru.compadre.mcp.mcp.client.common.model.McpServerInfo
import ru.compadre.mcp.mcp.client.common.model.McpToolDescriptor
import ru.compadre.mcp.mcp.client.common.toolcall.model.McpToolCallRequest
import ru.compadre.mcp.mcp.client.common.toolcall.model.McpToolCallResult

class StatelessMcpClientTest {
    @Test
    fun toolCallRequestKeepsProjectLevelContract() {
        val request = McpToolCallRequest(
            toolName = "fetch_post",
            arguments = mapOf("postId" to 1),
        )

        assertEquals("fetch_post", request.toolName)
        assertEquals(1, request.arguments["postId"])
    }

    @Test
    fun toolCallResultKeepsProjectLevelContract() {
        val result = McpToolCallResult(
            toolName = "fetch_post",
            isError = false,
            content = listOf("Публикация #1"),
            structuredContent = buildJsonObject {
                put("postId", 1)
            },
        )

        assertEquals("fetch_post", result.toolName)
        assertEquals(false, result.isError)
        assertEquals(listOf("Публикация #1"), result.content)
        assertEquals("1", result.structuredContent?.get("postId")?.toString())
    }

    @Test
    fun sdkCallToolResultMapsToProjectResult() {
        val result = toToolCallResult(
            toolName = "fetch_post",
            result = CallToolResult(
                content = listOf(
                    TextContent("Публикация #1"),
                    TextContent("Автор: 1"),
                ),
                isError = false,
                structuredContent = buildJsonObject {
                    put("postId", 1)
                },
            ),
        )

        assertEquals(
            McpToolCallResult(
                toolName = "fetch_post",
                isError = false,
                content = listOf("Публикация #1", "Автор: 1"),
                structuredContent = buildJsonObject {
                    put("postId", 1)
                },
            ),
            result,
        )
        assertNotNull(result.structuredContent)
    }

    @Test
    fun clientReusesSingleSessionPerEndpoint() = runBlocking {
        var createdSessions = 0
        val fakeSession = object : StatelessClientSession {
            override val endpoint: String = "http://127.0.0.1:3000/mcp"

            override suspend fun snapshot(): McpConnectionSnapshot = McpConnectionSnapshot(
                endpoint = endpoint,
                serverInfo = McpServerInfo(
                    name = "local_mcp_server",
                    version = "0.1.0",
                    title = "Local MCP Server",
                ),
                tools = listOf(McpToolDescriptor(name = "fetch_post", title = "Fetch Post")),
            )

            override suspend fun callTool(request: McpToolCallRequest): McpToolCallResult = McpToolCallResult(
                toolName = request.toolName,
                isError = false,
                content = listOf("ok"),
            )

            override suspend fun close() = Unit
        }
        val client = StatelessMcpClient(
            sessionRegistry = DefaultStatelessClientSessionRegistry {
                createdSessions++
                fakeSession
            },
        )

        client.connect("http://127.0.0.1:3000/mcp")
        client.callTool(
            endpoint = "http://127.0.0.1:3000/mcp",
            request = McpToolCallRequest(
                toolName = "fetch_post",
                arguments = mapOf("postId" to 1),
            ),
        )

        assertEquals(1, createdSessions)
    }

    @Test
    fun clientCloseDisposesRegistrySessions() = runBlocking {
        var closed = 0
        val client = StatelessMcpClient(
            sessionRegistry = DefaultStatelessClientSessionRegistry {
                object : StatelessClientSession {
                    override val endpoint: String = "http://127.0.0.1:3000/mcp"

                    override suspend fun snapshot(): McpConnectionSnapshot = McpConnectionSnapshot(
                        endpoint = endpoint,
                        serverInfo = McpServerInfo(
                            name = "local_mcp_server",
                            version = "0.1.0",
                            title = "Local MCP Server",
                        ),
                        tools = emptyList(),
                    )

                    override suspend fun callTool(request: McpToolCallRequest): McpToolCallResult = McpToolCallResult(
                        toolName = request.toolName,
                        isError = false,
                        content = emptyList(),
                    )

                    override suspend fun close() {
                        closed++
                    }
                }
            },
        )

        client.connect("http://127.0.0.1:3000/mcp")
        client.close()

        assertEquals(1, closed)
    }
}
