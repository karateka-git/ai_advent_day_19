package ru.compadre.mcp.client

import kotlinx.coroutines.runBlocking
import ru.compadre.mcp.config.McpProjectConfig
import ru.compadre.mcp.mcp.DefaultMcpClient
import ru.compadre.mcp.mcp.McpClient
import ru.compadre.mcp.mcp.model.McpConnectionSnapshot
import ru.compadre.mcp.mcp.model.McpToolDescriptor
import java.io.FileDescriptor
import java.io.FileOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets

/**
 * Точка входа минимального MCP client для сценария `initialize -> tools/list`.
 */
fun main(args: Array<String>): Unit = runBlocking {
    configureUtf8Console()
    when (parseClientCommand(args)) {
        ClientCommand.Connect -> runConnectCommand()
    }
}

internal enum class ClientCommand {
    Connect,
}

private suspend fun runConnectCommand() {
    val endpoint = McpProjectConfig.defaultEndpoint()
    val mcpClient: McpClient = DefaultMcpClient()
    val snapshot = mcpClient.connect(endpoint)

    printConnectionSummary(snapshot)
    println(renderToolsList(snapshot.tools))
}

internal fun parseClientCommand(args: Array<String>): ClientCommand {
    val rawCommand = args.firstOrNull()?.trim()?.lowercase() ?: "connect"

    return when (rawCommand) {
        "connect" -> ClientCommand.Connect
        else -> throw IllegalArgumentException(
            "Неизвестная команда клиента: `$rawCommand`. Поддерживаемые команды: connect.",
        )
    }
}

private fun configureUtf8Console() {
    System.setOut(
        PrintStream(
            FileOutputStream(FileDescriptor.out),
            true,
            StandardCharsets.UTF_8,
        ),
    )
    System.setErr(
        PrintStream(
            FileOutputStream(FileDescriptor.err),
            true,
            StandardCharsets.UTF_8,
        ),
    )
}

private fun printConnectionSummary(snapshot: McpConnectionSnapshot) {
    println("Connected to MCP server: ${snapshot.endpoint}")
    println("Server name: ${snapshot.serverInfo.name}")
    println("Server version: ${snapshot.serverInfo.version}")
    println("Server title: ${snapshot.serverInfo.title ?: "<unknown>"}")
    println("Server instructions: ${snapshot.serverInfo.instructions ?: "<none>"}")
}

internal fun renderToolsList(tools: List<McpToolDescriptor>): String {
    if (tools.isEmpty()) {
        return "Available tools: <none>"
    }

    val lines = buildList {
        add("Available tools (${tools.size}):")
        tools.forEachIndexed { index, tool ->
            add("${index + 1}. ${formatToolLine(tool)}")
        }
    }

    return lines.joinToString(separator = System.lineSeparator())
}

private fun formatToolLine(tool: McpToolDescriptor): String {
    val title = tool.title?.takeIf { it.isNotBlank() } ?: tool.name
    val description = tool.description?.takeIf { it.isNotBlank() } ?: "Описание не указано."
    return "$title [${tool.name}] - $description"
}
