package ru.compadre.mcp.mcp.client.stateless

import ru.compadre.mcp.mcp.client.McpClient
import ru.compadre.mcp.mcp.client.common.model.McpConnectionSnapshot
import ru.compadre.mcp.mcp.client.common.toolcall.model.McpToolCallRequest
import ru.compadre.mcp.mcp.client.common.toolcall.model.McpToolCallResult

class StatelessMcpClient internal constructor(
    private val sessionRegistry: StatelessClientSessionRegistry,
) : McpClient {
    constructor() : this(DefaultStatelessClientSessionRegistry())

    override suspend fun connect(endpoint: String): McpConnectionSnapshot =
        sessionRegistry.getOrCreate(endpoint).snapshot()

    override suspend fun callTool(endpoint: String, request: McpToolCallRequest): McpToolCallResult =
        sessionRegistry.getOrCreate(endpoint).callTool(request)

    suspend fun close() {
        sessionRegistry.closeAll()
    }
}
