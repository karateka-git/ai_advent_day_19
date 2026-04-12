package ru.compadre.mcp.application.service

import ru.compadre.mcp.application.command.Command
import ru.compadre.mcp.application.result.CommandResult

/**
 * Обработчик application-команд.
 */
interface ApplicationCommandHandler {
    /**
     * Выполняет переданную application-команду.
     */
    suspend fun handle(command: Command): CommandResult
}
