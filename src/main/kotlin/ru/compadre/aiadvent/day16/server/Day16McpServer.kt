package ru.compadre.aiadvent.day16.server

import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.ServerOptions
import io.modelcontextprotocol.kotlin.sdk.types.CallToolRequest
import io.modelcontextprotocol.kotlin.sdk.types.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.types.Implementation
import io.modelcontextprotocol.kotlin.sdk.types.ServerCapabilities
import io.modelcontextprotocol.kotlin.sdk.types.TextContent
import io.modelcontextprotocol.kotlin.sdk.types.ToolSchema
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject

/**
 * Создаёт минимальный экземпляр MCP server для проекта `day_16`.
 */
fun createDay16McpServer(): Server = Server(
    serverInfo = Implementation(
        name = "ai_advent_day_16_server",
        version = "0.1.0",
        title = "AI Advent Day 16 MCP Server",
    ),
    options = ServerOptions(
        capabilities = ServerCapabilities(
            tools = ServerCapabilities.Tools(listChanged = false),
        ),
    ),
    instructions = "Локальный MCP server для учебного проекта day_16.",
) {
    addTool(
        name = "ping",
        title = "Ping",
        description = "Возвращает короткий ответ сервера для проверки доступности.",
        inputSchema = ToolSchema(),
    ) {
        CallToolResult(
            content = listOf(TextContent("pong")),
            isError = false,
        )
    }

    addTool(
        name = "echo",
        title = "Echo",
        description = "Возвращает переданную строку обратно клиенту.",
        inputSchema = echoToolSchema(),
    ) { request ->
        val message = request.requiredStringArgument("message")
            ?: return@addTool CallToolResult(
                content = listOf(TextContent("Для инструмента echo требуется строковый аргумент `message`.")),
                isError = true,
            )

        CallToolResult(
            content = listOf(TextContent(message)),
            isError = false,
        )
    }
}

/**
 * Возвращает input schema для инструмента `echo`.
 */
private fun echoToolSchema(): ToolSchema = ToolSchema(
    properties = buildJsonObject {
        putJsonObject("message") {
            put("type", "string")
            put("description", "Строка, которую сервер должен вернуть обратно.")
        }
    },
    required = listOf("message"),
)

/**
 * Извлекает обязательный строковый аргумент инструмента из JSON-аргументов вызова.
 */
private fun CallToolRequest.requiredStringArgument(name: String): String? =
    arguments
        ?.get(name)
        ?.jsonPrimitive
        ?.contentOrNull
