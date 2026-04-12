package ru.compadre.mcp.presentation.cli

import ru.compadre.mcp.application.result.CommandResult
import ru.compadre.mcp.application.result.ConnectResult

/**
 * Стандартный форматтер application-результатов для CLI.
 */
class DefaultCliOutputFormatter : CliOutputFormatter {
    override fun format(result: CommandResult): String = when (result) {
        is ConnectResult -> formatConnectResult(result)
    }

    private fun formatConnectResult(result: ConnectResult): String {
        if (!result.connected) {
            return buildList {
                add("Failed to connect to MCP server: ${result.endpoint}")
                add("Error: ${result.errorMessage ?: "<unknown>"}")
            }.joinToString(separator = System.lineSeparator())
        }

        val lines = buildList {
            add("Connected to MCP server: ${result.endpoint}")
            add("Server name: ${result.serverName ?: "<unknown>"}")
            add("Server version: ${result.serverVersion ?: "<unknown>"}")
            add("Server title: ${result.serverTitle ?: "<unknown>"}")
            add("Server instructions: ${result.serverInstructions ?: "<none>"}")

            if (result.tools.isEmpty()) {
                add("Available tools: <none>")
            } else {
                add("Available tools (${result.tools.size}):")
                result.tools.forEachIndexed { index, tool ->
                    val title = tool.title?.takeIf { it.isNotBlank() } ?: tool.name
                    val description = tool.description?.takeIf { it.isNotBlank() } ?: "Описание не указано."
                    add("${index + 1}. $title [${tool.name}] - $description")
                }
            }
        }

        return lines.joinToString(separator = System.lineSeparator())
    }
}
