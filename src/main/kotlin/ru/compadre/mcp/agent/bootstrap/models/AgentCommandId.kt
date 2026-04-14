package ru.compadre.mcp.agent.bootstrap.models

enum class AgentCommandId {
    TOOL_POSTS,
    TOOL_POST,
    TOOL_START_RANDOM_POSTS,
    TOOL_SUMMARY_POSTS,
    TOOL_SUMMARY_SAVED,
    TOOL_SUMMARIES;

    fun value(): String = when (this) {
        TOOL_POSTS -> "tool.posts"
        TOOL_POST -> "tool.post"
        TOOL_START_RANDOM_POSTS -> "tool.start-random-posts"
        TOOL_SUMMARY_POSTS -> "tool.summary.posts"
        TOOL_SUMMARY_SAVED -> "tool.summary.saved"
        TOOL_SUMMARIES -> "tool.summaries"
    }
}
