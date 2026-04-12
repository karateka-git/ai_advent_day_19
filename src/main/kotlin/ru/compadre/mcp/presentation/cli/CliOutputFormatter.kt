package ru.compadre.mcp.presentation.cli

import ru.compadre.mcp.workflow.result.CommandResult

/**
 * Контракт форматирования результата workflow-слоя для CLI.
 */
interface CliOutputFormatter {
    /**
     * Преобразует результат команды в текстовый вывод для консоли.
     */
    fun format(result: CommandResult): String
}
