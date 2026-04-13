package ru.compadre.mcp.agent

import ru.compadre.mcp.agent.bootstrap.AgentCapabilityRegistry
import ru.compadre.mcp.agent.bootstrap.models.AgentCapabilitySnapshot
import ru.compadre.mcp.agent.bootstrap.models.KnownMcpServer
import ru.compadre.mcp.agent.bootstrap.models.PreparedMcpServer
import ru.compadre.mcp.mcp.client.McpClient

/**
 * Стандартная реализация агентного слоя поверх проектного MCP-клиента.
 */
class DefaultAgent(
    private val mcpClient: McpClient,
    private val capabilityRegistry: AgentCapabilityRegistry = AgentCapabilityRegistry(),
) : Agent {
    override suspend fun handle(request: AgentRequest): AgentResponse = when (request) {
        is AgentRequest.Prepare -> handlePrepare(request)
        is AgentRequest.Connect -> handleConnect(request)
        is AgentRequest.CallTool -> handleCallTool(request)
    }

    private suspend fun handlePrepare(request: AgentRequest.Prepare): AgentResponse =
        runCatching {
            val snapshot = AgentCapabilitySnapshot(
                servers = request.servers.map { server -> prepareServer(server) },
            )

            capabilityRegistry.replace(snapshot)
            AgentResponse.PreparationSuccess(snapshot)
        }.getOrElse { error ->
            AgentResponse.Failure(
                message = error.message ?: "Не удалось выполнить агентный запрос подготовки MCP-возможностей.",
            )
        }

    private suspend fun handleConnect(request: AgentRequest.Connect): AgentResponse =
        runCatching {
            val snapshot = mcpClient.connect(request.endpoint)

            AgentResponse.ConnectSuccess(
                endpoint = snapshot.endpoint,
                serverInfo = snapshot.serverInfo,
                tools = snapshot.tools,
            )
        }.getOrElse { error ->
            AgentResponse.Failure(
                message = error.message ?: "Не удалось выполнить агентный запрос connect.",
            )
        }

    private suspend fun handleCallTool(request: AgentRequest.CallTool): AgentResponse =
        runCatching {
            AgentResponse.ToolCallSuccess(
                endpoint = request.endpoint,
                result = mcpClient.callTool(
                    endpoint = request.endpoint,
                    request = request.toolCallRequest,
                ),
            )
        }.getOrElse { error ->
            AgentResponse.Failure(
                message = error.message ?: "Не удалось выполнить агентный запрос callTool.",
            )
        }

    private suspend fun prepareServer(server: KnownMcpServer): PreparedMcpServer =
        runCatching {
            val snapshot = mcpClient.connect(server.endpoint)

            PreparedMcpServer(
                serverId = server.serverId,
                endpoint = snapshot.endpoint,
                prepared = true,
                serverInfo = snapshot.serverInfo,
                tools = snapshot.tools,
            )
        }.getOrElse { error ->
            PreparedMcpServer(
                serverId = server.serverId,
                endpoint = server.endpoint,
                prepared = false,
                errorMessage = error.message ?: "Не удалось подготовить MCP-сервер `${server.serverId}`.",
            )
        }
}
