package ru.compadre.mcp.workflow.service

import ru.compadre.mcp.workflow.command.Command
import ru.compadre.mcp.workflow.result.CommandResult

/**
 * Обработчик workflow-команд.
 */
interface WorkflowCommandHandler {
    /**
     * Выполняет переданную workflow-команду.
     */
    suspend fun handle(command: Command): CommandResult
}
