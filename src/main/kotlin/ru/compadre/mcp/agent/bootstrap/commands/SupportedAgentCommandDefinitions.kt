package ru.compadre.mcp.agent.bootstrap.commands

fun supportedAgentCommandDefinitions(): List<AgentCommandDefinition> = listOf(
    ToolPostsAgentCommandDefinition(),
    ToolPostAgentCommandDefinition(),
    ToolStartRandomPostsAgentCommandDefinition(),
    ToolSummaryPostsAgentCommandDefinition(),
    ToolSummarySavedAgentCommandDefinition(),
    ToolSummariesAgentCommandDefinition(),
)
