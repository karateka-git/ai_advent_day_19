package ru.compadre.mcp.application.result

/**
 * Результат выполнения команды подключения к MCP.
 */
data class ConnectResult(
    val endpoint: String,
    val connected: Boolean,
    val serverName: String? = null,
    val serverVersion: String? = null,
    val serverTitle: String? = null,
    val serverInstructions: String? = null,
    val tools: List<ConnectToolResult> = emptyList(),
    val errorMessage: String? = null,
) : CommandResult

/**
 * Краткое описание инструмента в результате подключения.
 */
data class ConnectToolResult(
    val name: String,
    val title: String? = null,
    val description: String? = null,
)
