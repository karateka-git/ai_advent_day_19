package ru.compadre.mcp.presentation.cli

import ru.compadre.mcp.workflow.command.Command
import ru.compadre.mcp.workflow.command.ConnectCommand
import ru.compadre.mcp.workflow.command.ToolPostCommand
import ru.compadre.mcp.workflow.command.ToolPostsCommand

/**
 * Стандартный CLI-разборщик команд проекта.
 */
class DefaultCliCommandParser(
    private val defaultEndpoint: () -> String,
) : CliCommandParser {
    override fun parse(args: Array<String>): Command {
        val rawCommand = args.firstOrNull()
            ?.trim()
            ?.trimStart('\uFEFF')
            ?.lowercase()
            ?: throw IllegalArgumentException(
                "Команда не указана. Поддерживаемые команды: connect, tool posts, tool post <postId>.",
            )

        return when (rawCommand) {
            "connect" -> ConnectCommand(endpointOverride = defaultEndpoint())
            "tool" -> parseToolCommand(args)
            else -> throw IllegalArgumentException(
                "Неизвестная команда клиента: `$rawCommand`. Поддерживаемые команды: connect, tool posts, tool post <postId>.",
            )
        }
    }

    private fun parseToolCommand(args: Array<String>): Command {
        val toolName = args.getOrNull(1)
            ?.trim()
            ?.lowercase()
            ?: throw IllegalArgumentException(
                "Не указано имя tool-команды. Поддерживаемые форматы: tool posts, tool post <postId>.",
            )

        return when (toolName) {
            "posts" -> ToolPostsCommand(
                endpointOverride = defaultEndpoint(),
            )
            "post" -> {
                val postId = args.getOrNull(2)?.toIntOrNull()
                    ?: throw IllegalArgumentException(
                        "Для команды `tool post` требуется числовой аргумент `<postId>`.",
                    )

                ToolPostCommand(
                    endpointOverride = defaultEndpoint(),
                    postId = postId,
                )
            }

            else -> throw IllegalArgumentException(
                "Неизвестная tool-команда: `$toolName`. Поддерживаемые форматы: tool posts, tool post <postId>.",
            )
        }
    }
}
