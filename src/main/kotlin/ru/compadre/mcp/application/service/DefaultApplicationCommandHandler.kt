package ru.compadre.mcp.application.service

import ru.compadre.mcp.agent.Agent
import ru.compadre.mcp.agent.AgentRequest
import ru.compadre.mcp.agent.AgentResponse
import ru.compadre.mcp.application.command.Command
import ru.compadre.mcp.application.command.ConnectCommand
import ru.compadre.mcp.application.result.CommandResult
import ru.compadre.mcp.application.result.ConnectResult
import ru.compadre.mcp.application.result.ConnectToolResult

/**
 * Стандартная реализация обработчика application-команд.
 */
class DefaultApplicationCommandHandler(
    private val agent: Agent,
) : ApplicationCommandHandler {
    override suspend fun handle(command: Command): CommandResult = when (command) {
        is ConnectCommand -> handleConnect(command)
    }

    private suspend fun handleConnect(command: ConnectCommand): ConnectResult {
        val endpoint = command.endpointOverride
            ?: error("Для ConnectCommand требуется endpoint на этапе до внедрения presentation-слоя.")

        return when (val response = agent.handle(AgentRequest.Connect(endpoint))) {
            is AgentResponse.ConnectSuccess -> ConnectResult(
                endpoint = response.endpoint,
                connected = true,
                serverName = response.serverInfo.name,
                serverVersion = response.serverInfo.version,
                serverTitle = response.serverInfo.title,
                serverInstructions = response.serverInfo.instructions,
                tools = response.tools.map { tool ->
                    ConnectToolResult(
                        name = tool.name,
                        title = tool.title,
                        description = tool.description,
                    )
                },
            )
            is AgentResponse.Failure -> ConnectResult(
                endpoint = endpoint,
                connected = false,
                errorMessage = response.message,
            )
        }
    }
}
