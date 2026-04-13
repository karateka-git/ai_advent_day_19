package ru.compadre.mcp.mcp.server.fetchpost

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json

/**
 * HTTP-клиент к публичному mock API `JSONPlaceholder`.
 */
internal class JsonPlaceholderPostLookupClient(
    private val baseUrl: String = "https://jsonplaceholder.typicode.com",
    private val httpClient: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    },
) : PostLookupClient {
    override suspend fun fetchPost(postId: Int): JsonPlaceholderPost? {
        val response = httpClient.get("$baseUrl/posts/$postId")

        return when (response.status) {
            HttpStatusCode.OK -> response.body<JsonPlaceholderPost>()
            HttpStatusCode.NotFound -> null
            else -> error(
                "Mock API вернул неожиданный статус `${response.status.value}` для публикации `$postId`.",
            )
        }
    }
}
