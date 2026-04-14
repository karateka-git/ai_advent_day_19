package ru.compadre.mcp.workflow.command

/**
 * Команда чтения одной сохранённой summary из локального хранилища.
 */
data class ToolSummarySavedCommand(
    val summaryId: String,
) : Command
