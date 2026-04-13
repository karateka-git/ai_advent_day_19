package ru.compadre.mcp.presentation.cli

import ru.compadre.mcp.workflow.result.CommandResult
import ru.compadre.mcp.workflow.result.ConnectResult
import ru.compadre.mcp.workflow.result.ConnectToolResult
import ru.compadre.mcp.workflow.result.ToolCallResult

/**
 * Стандартный форматтер workflow-результатов для CLI.
 */
class DefaultCliOutputFormatter : CliOutputFormatter {
    override fun format(result: CommandResult): String = when (result) {
        is ConnectResult -> formatConnectResult(result)
        is ToolCallResult -> formatToolCallResult(result)
    }

    private fun formatConnectResult(result: ConnectResult): String {
        if (!result.connected) {
            return buildList {
                add("Не удалось подключиться к MCP-серверу: ${result.endpoint}")
                add("Ошибка: ${result.errorMessage ?: "<неизвестно>"}")
            }.joinToString(separator = System.lineSeparator())
        }

        val cliTools = result.tools.mapNotNull(::toCliToolUsage)

        val lines = buildList {
            add("Подключение к MCP-серверу установлено: ${result.endpoint}")
            add("Имя сервера: ${result.serverName ?: "<неизвестно>"}")
            add("Версия сервера: ${result.serverVersion ?: "<неизвестно>"}")
            add("Заголовок сервера: ${result.serverTitle ?: "<неизвестно>"}")
            add("Инструкции сервера: ${result.serverInstructions ?: "<нет>"}")

            if (cliTools.isEmpty()) {
                add("CLI-команды для доступных инструментов: <нет>")
            } else {
                add("CLI-команды для доступных инструментов (${cliTools.size}):")
                cliTools.forEachIndexed { index, tool ->
                    add("${index + 1}. ${tool.title} [${tool.name}] -> ${tool.command}")
                }
            }
        }

        return lines.joinToString(separator = System.lineSeparator())
    }

    private fun formatToolCallResult(result: ToolCallResult): String {
        if (!result.successful) {
            return buildList {
                add("Не удалось выполнить инструмент `${result.toolName}` через MCP: ${result.endpoint}")
                add("Ошибка: ${result.errorMessage ?: "<неизвестно>"}")
            }.joinToString(separator = System.lineSeparator())
        }

        return buildList {
            add("Инструмент `${result.toolName}` выполнен успешно: ${result.endpoint}")
            addAll(result.content)
        }.joinToString(separator = System.lineSeparator())
    }

    private fun toCliToolUsage(tool: ConnectToolResult): CliToolUsage? = when (tool.name) {
        "list_posts" -> CliToolUsage(
            name = tool.name,
            title = tool.title?.takeIf { it.isNotBlank() } ?: "List Posts",
            command = "tool posts",
        )
        "fetch_post" -> CliToolUsage(
            name = tool.name,
            title = tool.title?.takeIf { it.isNotBlank() } ?: "Fetch Post",
            command = "tool post <postId>",
        )
        else -> null
    }
}

private data class CliToolUsage(
    val name: String,
    val title: String,
    val command: String,
)
