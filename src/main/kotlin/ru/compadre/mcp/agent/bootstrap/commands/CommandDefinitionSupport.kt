package ru.compadre.mcp.agent.bootstrap.commands

import ru.compadre.mcp.agent.bootstrap.models.AvailableAgentCommand
import ru.compadre.mcp.agent.bootstrap.models.PreparedMcpServer

/**
 * Общая support-логика для command definitions первого прохода refactor-а.
 */
internal abstract class ToolBasedAgentCommandDefinition(
    override val commandId: String,
    override val cliPattern: String,
    override val description: String,
    private val toolName: String,
) : AgentCommandDefinition {
    override fun resolve(servers: List<PreparedMcpServer>): AvailableAgentCommand? {
        val server = servers.firstOrNull { preparedServer ->
            preparedServer.prepared && preparedServer.tools.any { tool -> tool.name == toolName }
        } ?: return null

        return AvailableAgentCommand(
            commandId = commandId,
            cliPattern = cliPattern,
            description = description,
            toolName = toolName,
            serverId = server.serverId,
            endpoint = server.endpoint,
        )
    }
}
