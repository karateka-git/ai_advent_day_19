package ru.compadre.mcp.agent

/**
 * Базовый контракт запроса к агенту.
 */
sealed interface AgentRequest {
    /**
     * Запрос агенту на подключение к MCP server и получение базовой информации.
     */
    data class Connect(
        val endpoint: String,
    ) : AgentRequest
}
