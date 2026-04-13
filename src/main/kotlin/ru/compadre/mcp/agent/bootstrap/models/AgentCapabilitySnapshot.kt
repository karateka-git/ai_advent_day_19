package ru.compadre.mcp.agent.bootstrap.models

/**
 * Снимок возможностей агента после выполнения стартовой подготовки.
 */
data class AgentCapabilitySnapshot(
    val servers: List<PreparedMcpServer> = emptyList(),
    val availableCommands: List<AvailableAgentCommand> = emptyList(),
)
