package ru.compadre.mcp.agent.bootstrap.commands

import ru.compadre.mcp.agent.bootstrap.models.AvailableAgentCommand
import ru.compadre.mcp.agent.bootstrap.models.PreparedMcpServer

/**
 * Базовый контракт определения пользовательской команды агента поверх MCP-возможностей.
 */
interface AgentCommandDefinition {
    val commandId: String
    val cliPattern: String
    val description: String

    /**
     * Пытается разрешить пользовательскую команду на основе подготовленных MCP-серверов.
     */
    fun resolve(servers: List<PreparedMcpServer>): AvailableAgentCommand?
}
