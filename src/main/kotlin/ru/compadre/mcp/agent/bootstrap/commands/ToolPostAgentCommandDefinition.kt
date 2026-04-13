package ru.compadre.mcp.agent.bootstrap.commands

/**
 * Definition пользовательской команды получения публикации из JSONPlaceholder.
 */
internal class ToolPostAgentCommandDefinition : ToolBasedAgentCommandDefinition(
    commandId = "tool.post",
    cliPattern = "tool post <postId>",
    description = "Показать публикацию из JSONPlaceholder по идентификатору.",
    toolName = "fetch_post",
)
