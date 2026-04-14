package ru.compadre.mcp.mcp.server.common.summarypipeline.tools.getsavedsummary

import io.modelcontextprotocol.kotlin.sdk.types.TextContent
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import ru.compadre.mcp.mcp.server.common.summarypipeline.models.SavedSummary
import ru.compadre.mcp.mcp.server.common.summarypipeline.models.SummaryDraft
import ru.compadre.mcp.mcp.server.common.summarypipeline.storage.SummaryStorage

class GetSavedSummaryToolTest {
    @Test
    fun getSavedSummaryReturnsRequestedEntry() {
        val result = getSavedSummaryToolResult(
            arguments = buildJsonObject {
                put("summaryId", "summary-2")
            },
            summaryStorage = object : SummaryStorage {
                override fun save(draft: SummaryDraft): SavedSummary = error("save should not be called")

                override fun list(): List<SavedSummary> = error("list should not be called")

                override fun get(summaryId: String): SavedSummary? = SavedSummary(
                    summaryId = "internal-uuid",
                    displayId = "summary-2",
                    savedAt = "2026-04-14T13:00:00Z",
                    title = "Короткие публикации: demo",
                    content = "Summary content",
                    sourcePostIds = listOf(3, 4, 5),
                    strategy = "short",
                )
            },
        )

        assertEquals(false, result.isError)
        val content = assertIs<TextContent>(result.content.single())
        assertEquals(true, content.text.contains("[summary-2]"))
        val structuredContent = assertNotNull(result.structuredContent)
        assertEquals("summary-2", structuredContent.getValue("displayId").toString().trim('"'))
    }
}
