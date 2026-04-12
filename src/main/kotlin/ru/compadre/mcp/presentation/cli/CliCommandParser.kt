package ru.compadre.mcp.presentation.cli

import ru.compadre.mcp.application.command.Command

/**
 * Контракт разбора CLI-ввода в application-команду.
 */
interface CliCommandParser {
    /**
     * Разбирает CLI-аргументы в application-команду.
     */
    fun parse(args: Array<String>): Command
}
