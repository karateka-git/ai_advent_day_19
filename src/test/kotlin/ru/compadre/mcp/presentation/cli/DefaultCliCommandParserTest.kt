package ru.compadre.mcp.presentation.cli

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import ru.compadre.mcp.application.command.ConnectCommand

class DefaultCliCommandParserTest {
    @Test
    fun parseDefaultsToConnectCommand() {
        val parser = DefaultCliCommandParser { "http://127.0.0.1:3000/mcp" }

        val command = parser.parse(emptyArray())

        assertIs<ConnectCommand>(command)
        assertEquals("http://127.0.0.1:3000/mcp", command.endpointOverride)
    }

    @Test
    fun parseAcceptsExplicitConnectCommand() {
        val parser = DefaultCliCommandParser { "http://127.0.0.1:3000/mcp" }

        val command = parser.parse(arrayOf("connect"))

        assertIs<ConnectCommand>(command)
        assertEquals("http://127.0.0.1:3000/mcp", command.endpointOverride)
    }

    @Test
    fun parseRejectsUnknownCommand() {
        val parser = DefaultCliCommandParser { "http://127.0.0.1:3000/mcp" }

        assertFailsWith<IllegalArgumentException> {
            parser.parse(arrayOf("tools"))
        }
    }
}
