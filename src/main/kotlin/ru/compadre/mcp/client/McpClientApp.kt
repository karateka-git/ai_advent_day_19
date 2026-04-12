package ru.compadre.mcp.client

import ru.compadre.mcp.config.McpProjectConfig

/**
 * Временная точка входа клиента для следующего этапа реализации MCP client.
 */
fun main() {
    println(
        "MCP client entrypoint is prepared. " +
            "Default server endpoint: ${McpProjectConfig.defaultEndpoint()}"
    )
}
