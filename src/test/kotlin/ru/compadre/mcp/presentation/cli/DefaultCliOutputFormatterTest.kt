package ru.compadre.mcp.presentation.cli

import kotlin.test.Test
import kotlin.test.assertEquals
import ru.compadre.mcp.application.result.ConnectResult
import ru.compadre.mcp.application.result.ConnectToolResult

class DefaultCliOutputFormatterTest {
    private val formatter = DefaultCliOutputFormatter()

    @Test
    fun formatRendersSuccessfulConnectResult() {
        val result = ConnectResult(
            endpoint = "http://127.0.0.1:3000/mcp",
            connected = true,
            serverName = "local_mcp_server",
            serverVersion = "0.1.0",
            serverTitle = "Local MCP Server",
            serverInstructions = "Локальный MCP server для sandbox-проекта.",
            tools = listOf(
                ConnectToolResult(
                    name = "ping",
                    title = "Ping",
                    description = "Возвращает короткий ответ сервера для проверки доступности.",
                ),
                ConnectToolResult(
                    name = "echo",
                    title = "Echo",
                    description = "Возвращает переданную строку обратно клиенту.",
                ),
            ),
        )

        val expected = listOf(
            "Connected to MCP server: http://127.0.0.1:3000/mcp",
            "Server name: local_mcp_server",
            "Server version: 0.1.0",
            "Server title: Local MCP Server",
            "Server instructions: Локальный MCP server для sandbox-проекта.",
            "Available tools (2):",
            "1. Ping [ping] - Возвращает короткий ответ сервера для проверки доступности.",
            "2. Echo [echo] - Возвращает переданную строку обратно клиенту.",
        ).joinToString(System.lineSeparator())

        assertEquals(expected, formatter.format(result))
    }

    @Test
    fun formatRendersFailedConnectResult() {
        val result = ConnectResult(
            endpoint = "http://127.0.0.1:3000/mcp",
            connected = false,
            errorMessage = "boom",
        )

        val expected = listOf(
            "Failed to connect to MCP server: http://127.0.0.1:3000/mcp",
            "Error: boom",
        ).joinToString(System.lineSeparator())

        assertEquals(expected, formatter.format(result))
    }
}
