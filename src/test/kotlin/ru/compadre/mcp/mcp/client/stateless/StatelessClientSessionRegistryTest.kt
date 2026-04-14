package ru.compadre.mcp.mcp.client.stateless

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class StatelessClientSessionRegistryTest {
    @Test
    fun registryReturnsSameSessionForSameEndpoint() = runBlocking {
        var createdSessions = 0
        val registry = DefaultStatelessClientSessionRegistry { endpoint ->
            createdSessions++
            fakeSession(endpoint)
        }

        val first = registry.getOrCreate("http://127.0.0.1:3000/mcp")
        val second = registry.getOrCreate("http://127.0.0.1:3000/mcp")

        assertEquals(first, second)
        assertEquals(1, createdSessions)
    }

    @Test
    fun registryClosesAllSessionsAndCreatesFreshOnNextAccess() = runBlocking {
        var createdSessions = 0
        var closedSessions = 0
        val registry = DefaultStatelessClientSessionRegistry { endpoint ->
            createdSessions++
            fakeSession(
                endpoint = endpoint,
                onClose = { closedSessions++ },
            )
        }

        registry.getOrCreate("http://127.0.0.1:3000/mcp")
        registry.closeAll()
        registry.getOrCreate("http://127.0.0.1:3000/mcp")

        assertEquals(2, createdSessions)
        assertEquals(1, closedSessions)
    }

    private fun fakeSession(
        endpoint: String,
        onClose: () -> Unit = {},
    ): StatelessClientSession = object : StatelessClientSession {
        override val endpoint: String = endpoint

        override suspend fun snapshot() = error("not used")

        override suspend fun callTool(request: ru.compadre.mcp.mcp.client.common.toolcall.model.McpToolCallRequest) =
            error("not used")

        override suspend fun close() {
            onClose()
        }
    }
}
