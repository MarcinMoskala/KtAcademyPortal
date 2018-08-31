package org.kotlinacademy.respositories

import org.kotlinacademy.data.Snippet
import org.kotlinacademy.data.SnippetData

interface SnippetRepository {

    suspend fun propose(snippetData: SnippetData)
    suspend fun update(snippet: Snippet, secret: String)
}