package ru.compadre.mcp.mcp.client.common.toolcall.model

import kotlinx.serialization.json.JsonObject

/**
 * Проектная модель результата вызова MCP-инструмента.
 *
 * @property toolName имя вызванного инструмента
 * @property isError признак ошибки вызова инструмента
 * @property content читаемое содержимое результата
 * @property structuredContent machine-readable payload если инструмент его вернул
 */
data class McpToolCallResult(
    val toolName: String,
    val isError: Boolean,
    val content: List<String>,
    val structuredContent: JsonObject? = null,
)
