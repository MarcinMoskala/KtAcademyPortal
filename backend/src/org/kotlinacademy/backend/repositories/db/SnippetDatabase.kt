package org.kotlinacademy.backend.repositories.db

import org.jetbrains.squash.connection.Transaction
import org.jetbrains.squash.expressions.eq
import org.jetbrains.squash.query.select
import org.jetbrains.squash.query.where
import org.jetbrains.squash.results.ResultRow
import org.jetbrains.squash.results.get
import org.jetbrains.squash.statements.*
import org.kotlinacademy.backend.repositories.db.Database.makeTransaction
import org.kotlinacademy.data.*
import org.kotlinacademy.now
import org.kotlinacademy.parseDateTime

class SnippetDatabase : SnippetDatabaseRepository {

    override suspend fun getSnippets(): List<Snippet> = makeTransaction {
        selectWholeSnippet()
                .execute()
                .map(::toSnippet)
                .toList()
    }

    override suspend fun getSnippet(id: Int): Snippet = makeTransaction {
        selectWholeSnippet()
                .where { SnippetTable.id eq id }
                .execute()
                .map(::toSnippet)
                .toList()
                .first()
    }

    override suspend fun addSnippet(snippetData: SnippetData, isAccepted: Boolean): Snippet = makeTransaction {
        val id = insertInto(SnippetTable).values {
            it[title] = snippetData.title
            it[code] = snippetData.code
            it[explanation] = snippetData.explanation
            it[author] = snippetData.author
            it[authorUrl] = snippetData.authorUrl
            it[dateTime] = now.toDateFormatString()
            it[accepted] = isAccepted
        }.fetch(SnippetTable.id).execute()

        return@makeTransaction Snippet(id, snippetData, now, false)
    }

    override suspend fun deleteSnippet(id: Int) = makeTransaction {
        deleteFrom(SnippetTable).where(SnippetTable.id eq id).execute()
    }

    override suspend fun updateSnippet(snippet: Snippet) = makeTransaction {
        val id = snippet.id
        require(countSnippetsWithId(id) == 1) { "Should be single snippet with id $id" }
        update(SnippetTable)
                .where { SnippetTable.id eq id }
                .set {
                    it[title] = snippet.title
                    it[code] = snippet.code
                    it[explanation] = snippet.explanation
                    it[author] = snippet.author
                    it[authorUrl] = snippet.authorUrl
                    it[dateTime] = snippet.dateTime.toDateFormatString()
                    it[accepted] = snippet.accepted
                }.execute()
    }

    private fun selectWholeSnippet() = SnippetTable.select(SnippetTable.id, SnippetTable.title, SnippetTable.code, SnippetTable.explanation, SnippetTable.author, SnippetTable.authorUrl, SnippetTable.dateTime, SnippetTable.accepted)

    private fun toSnippet(it: ResultRow) = Snippet(
            id = it[SnippetTable.id],
            data = SnippetData(
                    title = it[SnippetTable.title],
                    code = it[SnippetTable.code],
                    explanation = it[SnippetTable.explanation],
                    author = it[SnippetTable.author],
                    authorUrl = it[SnippetTable.authorUrl]
            ),
            dateTime = it[SnippetTable.dateTime].parseDateTime(),
            accepted = it[SnippetTable.accepted]
    )

    private fun Transaction.countSnippetsWithId(id: Int) = SnippetTable.select(SnippetTable.id)
            .where { SnippetTable.id.eq(id) }
            .execute()
            .count()
}