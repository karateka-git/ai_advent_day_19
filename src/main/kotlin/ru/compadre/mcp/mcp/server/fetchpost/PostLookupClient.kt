package ru.compadre.mcp.mcp.server.fetchpost

/**
 * Контракт доступа к источнику данных публикаций для MCP-инструмента `fetch_post`.
 */
internal interface PostLookupClient {
    /**
     * Возвращает публикацию по её идентификатору.
     *
     * @param postId идентификатор публикации
     * @return найденная публикация или `null`, если публикация не существует
     */
    suspend fun fetchPost(postId: Int): JsonPlaceholderPost?
}
