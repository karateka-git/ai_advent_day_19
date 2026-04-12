package ru.compadre.mcp.application.command

/**
 * Команда подключения к MCP server и запроса базовой информации.
 */
data class ConnectCommand(
    val endpointOverride: String? = null,
) : Command
