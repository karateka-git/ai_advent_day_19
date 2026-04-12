package ru.compadre.aiadvent.day16.server

import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.modelcontextprotocol.kotlin.sdk.server.mcpStreamableHttp
import ru.compadre.aiadvent.day16.config.McpProjectConfig

/**
 * Точка входа локального MCP server для sandbox-проекта.
 */
fun main() {
    val server = embeddedServer(
        factory = CIO,
        host = McpProjectConfig.SERVER_HOST,
        port = McpProjectConfig.SERVER_PORT,
    ) {
        mcpStreamableHttp(path = McpProjectConfig.MCP_PATH) {
            createDay16McpServer()
        }
    }

    println("Starting MCP server at ${McpProjectConfig.defaultEndpoint()}")
    server.start(wait = true)
}
