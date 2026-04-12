package ru.compadre.mcp.presentation.cli

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import ru.compadre.mcp.workflow.command.ConnectCommand

class DefaultCliCommandParserTest {
    @Test
    fun parseRejectsEmptyCommand() {
        val parser = DefaultCliCommandParser { "http://127.0.0.1:3000/mcp" }

        assertFailsWith<IllegalArgumentException> {
            parser.parse(emptyArray())
        }
    }

    @Test
    fun parseAcceptsExplicitConnectCommand() {
        val parser = DefaultCliCommandParser { "http://127.0.0.1:3000/mcp" }

        val command = parser.parse(arrayOf("connect"))

        assertIs<ConnectCommand>(command)
        assertEquals("http://127.0.0.1:3000/mcp", command.endpointOverride)
    }

    @Test
    fun parseAcceptsCommandWithBomPrefix() {
        val parser = DefaultCliCommandParser { "http://127.0.0.1:3000/mcp" }

        val command = parser.parse(arrayOf("\uFEFFconnect"))

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
