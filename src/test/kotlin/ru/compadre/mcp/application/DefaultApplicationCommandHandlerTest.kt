package ru.compadre.mcp.application

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import ru.compadre.mcp.agent.Agent
import ru.compadre.mcp.agent.AgentRequest
import ru.compadre.mcp.agent.AgentResponse
import ru.compadre.mcp.application.command.ConnectCommand
import ru.compadre.mcp.application.result.ConnectResult
import ru.compadre.mcp.application.service.DefaultApplicationCommandHandler
import ru.compadre.mcp.mcp.model.McpServerInfo
import ru.compadre.mcp.mcp.model.McpToolDescriptor

class DefaultApplicationCommandHandlerTest {
    @Test
    fun connectCommandReturnsSuccessfulConnectResult() = runBlocking {
        val handler = DefaultApplicationCommandHandler(
            agent = object : Agent {
                override suspend fun handle(request: AgentRequest): AgentResponse {
                    val connectRequest = request as AgentRequest.Connect
                    return AgentResponse.ConnectSuccess(
                        endpoint = connectRequest.endpoint,
                        serverInfo = McpServerInfo(
                            name = "local_mcp_server",
                            version = "0.1.0",
                            title = "Local MCP Server",
                            instructions = "Локальный MCP server для sandbox-проекта.",
                        ),
                        tools = listOf(
                            McpToolDescriptor(name = "ping", title = "Ping"),
                            McpToolDescriptor(name = "echo", title = "Echo"),
                        ),
                    )
                }
            },
        )

        val result = handler.handle(
            ConnectCommand(endpointOverride = "http://127.0.0.1:3000/mcp"),
        )

        assertIs<ConnectResult>(result)
        assertEquals(true, result.connected)
        assertEquals("local_mcp_server", result.serverName)
        assertEquals(2, result.tools.size)
    }

    @Test
    fun connectCommandReturnsFailureResultWhenAgentFails() = runBlocking {
        val handler = DefaultApplicationCommandHandler(
            agent = object : Agent {
                override suspend fun handle(request: AgentRequest): AgentResponse =
                    AgentResponse.Failure("agent failure")
            },
        )

        val result = handler.handle(
            ConnectCommand(endpointOverride = "http://127.0.0.1:3000/mcp"),
        )

        assertIs<ConnectResult>(result)
        assertEquals(false, result.connected)
        assertEquals("agent failure", result.errorMessage)
        assertEquals("http://127.0.0.1:3000/mcp", result.endpoint)
    }
}
