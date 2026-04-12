package ru.compadre.mcp.client

import io.modelcontextprotocol.kotlin.sdk.types.ListToolsResult
import io.modelcontextprotocol.kotlin.sdk.types.Tool
import io.modelcontextprotocol.kotlin.sdk.types.ToolSchema
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class McpClientAppTest {
    @Test
    fun parseClientCommandDefaultsToConnect() {
        assertEquals(ClientCommand.Connect, parseClientCommand(emptyArray()))
    }

    @Test
    fun parseClientCommandAcceptsConnect() {
        assertEquals(ClientCommand.Connect, parseClientCommand(arrayOf("connect")))
    }

    @Test
    fun parseClientCommandRejectsUnknownCommand() {
        assertFailsWith<IllegalArgumentException> {
            parseClientCommand(arrayOf("tools"))
        }
    }

    @Test
    fun renderToolsListFormatsRegisteredTools() {
        val result = ListToolsResult(
            tools = listOf(
                Tool(
                    name = "ping",
                    title = "Ping",
                    description = "Проверяет доступность сервера.",
                    inputSchema = ToolSchema(),
                ),
                Tool(
                    name = "echo",
                    description = "Возвращает строку обратно клиенту.",
                    inputSchema = ToolSchema(),
                ),
            ),
        )

        val rendered = renderToolsList(result)
        val expected = listOf(
            "Available tools (2):",
            "1. Ping [ping] - Проверяет доступность сервера.",
            "2. echo [echo] - Возвращает строку обратно клиенту.",
        ).joinToString(separator = System.lineSeparator())

        assertEquals(
            expected,
            rendered,
        )
    }

    @Test
    fun renderToolsListHandlesEmptyResult() {
        assertEquals("Available tools: <none>", renderToolsList(ListToolsResult(emptyList())))
    }
}
