package ru.compadre.mcp

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
 * Главная точка входа приложения, связывающая presentation, application, agent и MCP-слои.
 */
fun main(args: Array<String>): Unit = runBlocking {
    configureUtf8Console()

    val commandParser: CliCommandParser = DefaultCliCommandParser(McpProjectConfig::defaultEndpoint)
    val commandHandler: ApplicationCommandHandler = DefaultApplicationCommandHandler(
        agent = DefaultAgent(DefaultMcpClient()),
    )
    val outputFormatter: CliOutputFormatter = DefaultCliOutputFormatter()

    if (args.isEmpty()) {
        runInteractiveShell(
            commandParser = commandParser,
            commandHandler = commandHandler,
            outputFormatter = outputFormatter,
        )
        return@runBlocking
    }

    executeCommand(
        commandArgs = args,
        commandParser = commandParser,
        commandHandler = commandHandler,
        outputFormatter = outputFormatter,
    )
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

private suspend fun runInteractiveShell(
    commandParser: CliCommandParser,
    commandHandler: ApplicationCommandHandler,
    outputFormatter: CliOutputFormatter,
) {
    println("MCP client is ready.")
    println("Available commands: connect, help, exit")

    while (true) {
        print("> ")
        val rawInput = readlnOrNull()
            ?.trim()
            ?.trimStart('\uFEFF')
            ?: run {
                println("Stopping client session.")
                return
            }

        if (rawInput.isBlank()) {
            continue
        }

        when (rawInput.lowercase()) {
            "exit", "quit" -> {
                println("Stopping client session.")
                return
            }

            "help" -> {
                println("Available commands: connect, help, exit")
                continue
            }
        }

        executeCommand(
            commandArgs = rawInput.split(Regex("\\s+")).toTypedArray(),
            commandParser = commandParser,
            commandHandler = commandHandler,
            outputFormatter = outputFormatter,
        )
    }
}

private suspend fun executeCommand(
    commandArgs: Array<String>,
    commandParser: CliCommandParser,
    commandHandler: ApplicationCommandHandler,
    outputFormatter: CliOutputFormatter,
) {
    try {
        val command = commandParser.parse(commandArgs)
        val result = commandHandler.handle(command)
        val output = outputFormatter.format(result)
        println(output)
    } catch (error: IllegalArgumentException) {
        println(error.message ?: "Command parsing failed.")
    }
}
