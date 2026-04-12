package ru.compadre.mcp.presentation.cli

import ru.compadre.mcp.workflow.command.Command

/**
 * Контракт разбора CLI-ввода в workflow-команду.
 */
interface CliCommandParser {
    /**
     * Разбирает CLI-аргументы в workflow-команду.
     */
    fun parse(args: Array<String>): Command
}
