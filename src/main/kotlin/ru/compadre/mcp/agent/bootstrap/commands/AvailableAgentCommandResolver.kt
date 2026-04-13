package ru.compadre.mcp.agent.bootstrap.commands

import ru.compadre.mcp.agent.bootstrap.models.AvailableAgentCommand
import ru.compadre.mcp.agent.bootstrap.models.PreparedMcpServer

/**
 * Строит runtime-список пользовательских команд агента из набора command definitions.
 */
class AvailableAgentCommandResolver(
    private val definitions: List<AgentCommandDefinition> = supportedAgentCommandDefinitions(),
) {
    /**
     * Проходит по всем поддерживаемым командам и собирает только те, которые доступны на подготовленных серверах.
     */
    fun resolve(servers: List<PreparedMcpServer>): List<AvailableAgentCommand> = definitions.mapNotNull { definition ->
        definition.resolve(servers)
    }
}
