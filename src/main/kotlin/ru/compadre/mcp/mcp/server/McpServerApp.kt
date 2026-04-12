package ru.compadre.mcp.mcp.server

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.modelcontextprotocol.kotlin.sdk.server.mcpStatelessStreamableHttp
import ru.compadre.mcp.config.McpProjectConfig

/**
 * Точка входа локального MCP server для sandbox-проекта.
 */
fun main() {
    val server = embeddedServer(
        factory = CIO,
        host = McpProjectConfig.SERVER_HOST,
        port = McpProjectConfig.SERVER_PORT,
        module = Application::configureMcpServer,
    )

    println("Starting MCP server at ${McpProjectConfig.defaultEndpoint()}")
    server.start(wait = true)
}

/**
 * Конфигурирует Ktor-приложение для работы с локальным MCP server по stateless Streamable HTTP.
 */
fun Application.configureMcpServer() {
    install(ContentNegotiation) {
        json()
    }

    mcpStatelessStreamableHttp(path = McpProjectConfig.MCP_PATH) {
        createMcpServer()
    }
}
