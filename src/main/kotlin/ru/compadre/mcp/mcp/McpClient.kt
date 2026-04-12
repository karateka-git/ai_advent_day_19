package ru.compadre.mcp.mcp

import ru.compadre.mcp.mcp.model.McpConnectionSnapshot

/**
 * Проектный контракт доступа к MCP.
 */
interface McpClient {
    /**
     * Подключается к MCP server и возвращает снимок базовой информации.
     */
    suspend fun connect(endpoint: String): McpConnectionSnapshot
}
