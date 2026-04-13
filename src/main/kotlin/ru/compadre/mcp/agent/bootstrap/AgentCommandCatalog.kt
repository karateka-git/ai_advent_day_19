package ru.compadre.mcp.agent.bootstrap

import ru.compadre.mcp.agent.bootstrap.models.AvailableAgentCommand
import ru.compadre.mcp.agent.bootstrap.models.PreparedMcpServer

private data class AgentCommandDefinition(
    val commandId: String,
    val cliPattern: String,
    val description: String,
    val toolName: String,
)

private val supportedAgentCommands = listOf(
    AgentCommandDefinition(
        commandId = "tool.posts",
        cliPattern = "tool posts",
        description = "Показать первые 10 публикаций из JSONPlaceholder.",
        toolName = "list_posts",
    ),
    AgentCommandDefinition(
        commandId = "tool.post",
        cliPattern = "tool post <postId>",
        description = "Получить публикацию из JSONPlaceholder по идентификатору.",
        toolName = "fetch_post",
    ),
)

/**
 * Строит доступные пользователю команды на основе опубликованных MCP-инструментов.
 */
internal fun buildAvailableAgentCommands(
    servers: List<PreparedMcpServer>,
): List<AvailableAgentCommand> = supportedAgentCommands.mapNotNull { definition ->
    val server = servers.firstOrNull { preparedServer ->
        preparedServer.prepared && preparedServer.tools.any { tool -> tool.name == definition.toolName }
    } ?: return@mapNotNull null

    AvailableAgentCommand(
        commandId = definition.commandId,
        cliPattern = definition.cliPattern,
        description = definition.description,
        toolName = definition.toolName,
        serverId = server.serverId,
        endpoint = server.endpoint,
    )
}
