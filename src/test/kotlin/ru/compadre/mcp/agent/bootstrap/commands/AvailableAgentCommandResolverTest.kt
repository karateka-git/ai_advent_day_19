package ru.compadre.mcp.agent.bootstrap.commands

import kotlin.test.Test
import kotlin.test.assertEquals
import ru.compadre.mcp.agent.bootstrap.models.PreparedMcpServer
import ru.compadre.mcp.mcp.client.model.McpToolDescriptor

class AvailableAgentCommandResolverTest {
    @Test
    fun resolveBuildsCommandsFromExplicitDefinitions() {
        val resolver = AvailableAgentCommandResolver(
            definitions = supportedAgentCommandDefinitions(),
        )

        val result = resolver.resolve(
            servers = listOf(
                PreparedMcpServer(
                    serverId = "local_mcp_server",
                    endpoint = "http://127.0.0.1:3000/mcp",
                    prepared = true,
                    tools = listOf(
                        McpToolDescriptor(name = "list_posts", title = "List Posts"),
                        McpToolDescriptor(name = "fetch_post", title = "Fetch Post"),
                    ),
                ),
            ),
        )

        assertEquals(2, result.size)
        assertEquals("tool.posts", result.first().commandId)
        assertEquals("tool post <postId>", result.last().cliPattern)
    }

    @Test
    fun resolveSkipsDefinitionsThatCannotBeResolved() {
        val resolver = AvailableAgentCommandResolver(
            definitions = supportedAgentCommandDefinitions(),
        )

        val result = resolver.resolve(
            servers = listOf(
                PreparedMcpServer(
                    serverId = "local_mcp_server",
                    endpoint = "http://127.0.0.1:3000/mcp",
                    prepared = true,
                    tools = listOf(
                        McpToolDescriptor(name = "fetch_post", title = "Fetch Post"),
                    ),
                ),
            ),
        )

        assertEquals(1, result.size)
        assertEquals("tool.post", result.single().commandId)
    }
}
