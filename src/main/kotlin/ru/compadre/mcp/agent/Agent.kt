package ru.compadre.mcp.agent

/**
 * Агентный слой, который исполняет application-запросы через нижележащие интеграции.
 */
interface Agent {
    /**
     * Выполняет агентный запрос.
     */
    suspend fun handle(request: AgentRequest): AgentResponse
}
