package ru.compadre.mcp.mcp.server

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class McpServerTest {
    @Test
    fun serverRegistersExpectedTools() {
        val server = createMcpServer()

        assertEquals(setOf("ping", "echo"), server.tools.keys)
        assertTrue(server.tools["ping"] != null)
        assertTrue(server.tools["echo"] != null)
    }
}
