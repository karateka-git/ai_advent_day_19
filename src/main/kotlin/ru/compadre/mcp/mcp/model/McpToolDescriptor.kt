package ru.compadre.mcp.mcp.model

/**
 * Проектное описание MCP-инструмента.
 */
data class McpToolDescriptor(
    val name: String,
    val title: String? = null,
    val description: String? = null,
)
