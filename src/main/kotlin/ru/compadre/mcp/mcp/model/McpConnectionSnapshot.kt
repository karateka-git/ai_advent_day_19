package ru.compadre.mcp.mcp.model

/**
 * Снимок состояния после успешного подключения к MCP server.
 */
data class McpConnectionSnapshot(
    val endpoint: String,
    val serverInfo: McpServerInfo,
    val tools: List<McpToolDescriptor>,
)
