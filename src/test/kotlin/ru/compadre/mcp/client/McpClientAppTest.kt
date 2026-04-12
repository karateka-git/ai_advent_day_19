package ru.compadre.mcp.client

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import ru.compadre.mcp.mcp.model.McpToolDescriptor

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
        val tools = listOf(
            McpToolDescriptor(
                name = "ping",
                title = "Ping",
                description = "Проверяет доступность сервера.",
            ),
            McpToolDescriptor(
                name = "echo",
                description = "Возвращает строку обратно клиенту.",
            ),
        )

        val rendered = renderToolsList(tools)
        val expected = listOf(
            "Available tools (2):",
            "1. Ping [ping] - Проверяет доступность сервера.",
            "2. echo [echo] - Возвращает строку обратно клиенту.",
        ).joinToString(separator = System.lineSeparator())

        assertEquals(expected, rendered)
    }

    @Test
    fun renderToolsListHandlesEmptyResult() {
        assertEquals("Available tools: <none>", renderToolsList(emptyList()))
    }
}
