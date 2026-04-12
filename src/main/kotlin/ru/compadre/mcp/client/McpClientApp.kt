package ru.compadre.mcp.client

import kotlinx.coroutines.runBlocking
import ru.compadre.mcp.agent.DefaultAgent
import ru.compadre.mcp.application.service.ApplicationCommandHandler
import ru.compadre.mcp.application.service.DefaultApplicationCommandHandler
import ru.compadre.mcp.config.McpProjectConfig
import ru.compadre.mcp.mcp.DefaultMcpClient
import ru.compadre.mcp.presentation.cli.CliCommandParser
import ru.compadre.mcp.presentation.cli.CliOutputFormatter
import ru.compadre.mcp.presentation.cli.DefaultCliCommandParser
import ru.compadre.mcp.presentation.cli.DefaultCliOutputFormatter
import java.io.FileDescriptor
import java.io.FileOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets

/**
 * Точка входа минимального MCP client для сценария `initialize -> tools/list`.
 */
fun main(args: Array<String>): Unit = runBlocking {
    configureUtf8Console()

    val commandParser: CliCommandParser = DefaultCliCommandParser(McpProjectConfig::defaultEndpoint)
    val commandHandler: ApplicationCommandHandler = DefaultApplicationCommandHandler(
        agent = DefaultAgent(DefaultMcpClient()),
    )
    val outputFormatter: CliOutputFormatter = DefaultCliOutputFormatter()

    val command = commandParser.parse(args)
    val result = commandHandler.handle(command)
    val output = outputFormatter.format(result)

    println(output)
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
