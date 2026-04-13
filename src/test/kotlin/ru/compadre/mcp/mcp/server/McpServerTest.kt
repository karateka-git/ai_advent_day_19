package ru.compadre.mcp.mcp.server

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import ru.compadre.mcp.mcp.server.api.jsonplaceholder.JsonPlaceholderApiClient
import ru.compadre.mcp.mcp.server.api.jsonplaceholder.common.models.JsonPlaceholderPost

class McpServerTest {
    @Test
    fun serverRegistersExpectedTools() {
        val server = createMcpServer(
            jsonPlaceholderApiClient = object : JsonPlaceholderApiClient {
                override suspend fun fetchPost(postId: Int): JsonPlaceholderPost? = null

                override suspend fun fetchPosts(limit: Int): List<JsonPlaceholderPost> = emptyList()
            },
        )

        assertEquals(setOf("ping", "echo", "fetch_post", "list_posts"), server.tools.keys)
        assertTrue(server.tools["ping"] != null)
        assertTrue(server.tools["echo"] != null)
        assertTrue(server.tools["fetch_post"] != null)
        assertTrue(server.tools["list_posts"] != null)
    }
}
