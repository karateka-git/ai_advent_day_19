package ru.compadre.mcp.workflow.command

/**
 * Команда запуска автоматического pipeline summary по случайным публикациям.
 */
data class ToolSummaryPostsCommand(
    val count: Int,
    val strategy: String,
) : Command
