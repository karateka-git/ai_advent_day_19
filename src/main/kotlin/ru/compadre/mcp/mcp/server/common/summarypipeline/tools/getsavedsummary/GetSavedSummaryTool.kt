package ru.compadre.mcp.mcp.server.common.summarypipeline.tools.getsavedsummary

import io.modelcontextprotocol.kotlin.sdk.types.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.types.TextContent
import io.modelcontextprotocol.kotlin.sdk.types.ToolSchema
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject
import ru.compadre.mcp.mcp.server.common.summarypipeline.storage.SummaryStorage
import ru.compadre.mcp.mcp.server.common.summarypipeline.tools.summaryPipelineJson

fun getSavedSummaryToolSchema(): ToolSchema = ToolSchema(
    properties = buildJsonObject {
        putJsonObject("summaryId") {
            put("type", "string")
            put("description", "Идентификатор сохранённой summary, например `summary-1`.")
        }
    },
    required = listOf("summaryId"),
)

fun getSavedSummaryToolOutputSchema(): ToolSchema = ToolSchema(
    properties = buildJsonObject {
        putJsonObject("summaryId") { put("type", "string") }
        putJsonObject("displayId") { put("type", "string") }
        putJsonObject("savedAt") { put("type", "string") }
        putJsonObject("title") { put("type", "string") }
        putJsonObject("content") { put("type", "string") }
        putJsonObject("strategy") { put("type", "string") }
        putJsonObject("sourcePostIds") { put("type", "array") }
    },
    required = listOf("summaryId", "displayId", "savedAt", "title", "content", "strategy", "sourcePostIds"),
)

internal fun getSavedSummaryToolResult(
    arguments: JsonObject?,
    summaryStorage: SummaryStorage,
): CallToolResult {
    val summaryId = arguments.requiredStringArgument("summaryId")
        ?: return CallToolResult(
            content = listOf(TextContent("Для инструмента `get_saved_summary` требуется строковый аргумент `summaryId`.")),
            isError = true,
        )

    val summary = summaryStorage.get(summaryId)
        ?: return CallToolResult(
            content = listOf(TextContent("Summary `$summaryId` не найден.")),
            isError = true,
        )

    val content = buildString {
        appendLine("${summary.title} [${summary.displayId}]")
        appendLine("Стратегия: ${summary.strategy}")
        appendLine("Исходные публикации: ${summary.sourcePostIds.joinToString()}")
        appendLine("Сохранено: ${summary.savedAt}")
        appendLine()
        append(summary.content)
    }

    return CallToolResult(
        content = listOf(TextContent(content.trim())),
        isError = false,
        structuredContent = summaryPipelineJson.parseToJsonElement(
            summaryPipelineJson.encodeToString(summary),
        ).jsonObject,
    )
}

private fun JsonObject?.requiredStringArgument(name: String): String? =
    this?.get(name)
        ?.jsonPrimitive
        ?.contentOrNull
        ?.takeIf { it.isNotBlank() }
