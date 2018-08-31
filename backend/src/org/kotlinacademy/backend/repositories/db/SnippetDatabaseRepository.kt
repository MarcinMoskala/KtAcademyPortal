package org.kotlinacademy.backend.repositories.db

import org.kotlinacademy.backend.common.Provider
import org.kotlinacademy.data.Puzzler
import org.kotlinacademy.data.PuzzlerData
import org.kotlinacademy.data.Snippet
import org.kotlinacademy.data.SnippetData

interface SnippetDatabaseRepository {

    suspend fun getSnippets(): List<Snippet>
    suspend fun getSnippet(id: Int): Snippet
    suspend fun addSnippet(snippetData: SnippetData, isAccepted: Boolean): Snippet
    suspend fun deleteSnippet(id: Int)
    suspend fun updateSnippet(snippet: Snippet)

    companion object: Provider<SnippetDatabaseRepository>() {
        override fun create(): SnippetDatabaseRepository = Database.snippetDatabase
    }
}