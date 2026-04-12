package ru.compadre.mcp.workflow

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import ru.compadre.mcp.agent.AgentRequest
import ru.compadre.mcp.agent.AgentResponse
import ru.compadre.mcp.mcp.model.McpServerInfo
import ru.compadre.mcp.mcp.model.McpToolDescriptor
import ru.compadre.mcp.workflow.command.Command
import ru.compadre.mcp.workflow.command.ConnectCommand
import ru.compadre.mcp.workflow.result.CommandResult
import ru.compadre.mcp.workflow.result.ConnectResult
import ru.compadre.mcp.workflow.result.ConnectToolResult

class ArchitectureContractsTest {
    @Test
    fun connectCommandImplementsBaseCommandContract() {
        val command: Command = ConnectCommand(endpointOverride = "http://localhost:3000/mcp")

        assertIs<ConnectCommand>(command)
        assertEquals("http://localhost:3000/mcp", command.endpointOverride)
    }

    @Test
    fun connectResultImplementsBaseCommandResultContract() {
        val result: CommandResult = ConnectResult(
            endpoint = "http://127.0.0.1:3000/mcp",
            connected = true,
            serverName = "local_mcp_server",
            tools = listOf(
                ConnectToolResult(name = "ping", title = "Ping"),
            ),
        )

        assertIs<ConnectResult>(result)
        assertEquals(true, result.connected)
        assertEquals(1, result.tools.size)
    }

    @Test
    fun agentConnectResponseKeepsNormalizedMcpData() {
        val response: AgentResponse = AgentResponse.ConnectSuccess(
            endpoint = "http://127.0.0.1:3000/mcp",
            serverInfo = McpServerInfo(
                name = "local_mcp_server",
                version = "0.1.0",
                title = "Local MCP Server",
            ),
            tools = listOf(
                McpToolDescriptor(name = "ping", title = "Ping"),
                McpToolDescriptor(name = "echo", title = "Echo"),
            ),
        )

        assertIs<AgentResponse.ConnectSuccess>(response)
        assertEquals("local_mcp_server", response.serverInfo.name)
        assertEquals(2, response.tools.size)
    }

    @Test
    fun agentConnectRequestKeepsEndpoint() {
        val request: AgentRequest = AgentRequest.Connect(
            endpoint = "http://127.0.0.1:3000/mcp",
        )

        assertIs<AgentRequest.Connect>(request)
        assertEquals("http://127.0.0.1:3000/mcp", request.endpoint)
    }
}
