package ru.compadre.mcp.agent.bootstrap.commands

/**
 * Definition пользовательской команды списка публикаций из JSONPlaceholder.
 */
internal class ToolPostsAgentCommandDefinition : ToolBasedAgentCommandDefinition(
    commandId = "tool.posts",
    cliPattern = "tool posts",
    description = "Показать первые 10 публикаций из JSONPlaceholder.",
    toolName = "list_posts",
)
