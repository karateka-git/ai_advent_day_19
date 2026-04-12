package ru.compadre.aiadvent.day16.server

import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.ServerOptions
import io.modelcontextprotocol.kotlin.sdk.types.Implementation
import io.modelcontextprotocol.kotlin.sdk.types.ServerCapabilities

/**
 * Создаёт минимальный экземпляр MCP server для проекта `day_16`.
 */
fun createDay16McpServer(): Server = Server(
    serverInfo = Implementation(
        name = "ai_advent_day_16_server",
        version = "0.1.0",
        title = "AI Advent Day 16 MCP Server",
    ),
    options = ServerOptions(
        capabilities = ServerCapabilities(
            tools = ServerCapabilities.Tools(listChanged = false),
        ),
    ),
    instructions = "Локальный MCP server для учебного проекта day_16.",
)
