package ru.compadre.mcp.presentation.cli

import ru.compadre.mcp.application.command.Command
import ru.compadre.mcp.application.command.ConnectCommand

/**
 * Стандартный CLI-разборщик команд проекта.
 */
class DefaultCliCommandParser(
    private val defaultEndpoint: () -> String,
) : CliCommandParser {
    override fun parse(args: Array<String>): Command {
        val rawCommand = args.firstOrNull()?.trim()?.lowercase() ?: "connect"

        return when (rawCommand) {
            "connect" -> ConnectCommand(endpointOverride = defaultEndpoint())
            else -> throw IllegalArgumentException(
                "Неизвестная команда клиента: `$rawCommand`. Поддерживаемые команды: connect.",
            )
        }
    }
}
