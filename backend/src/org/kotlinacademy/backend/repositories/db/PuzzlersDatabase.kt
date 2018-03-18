package org.kotlinacademy.backend.repositories.db

import org.jetbrains.squash.connection.Transaction
import org.jetbrains.squash.expressions.eq
import org.jetbrains.squash.query.select
import org.jetbrains.squash.query.where
import org.jetbrains.squash.results.ResultRow
import org.jetbrains.squash.results.get
import org.jetbrains.squash.statements.*
import org.kotlinacademy.backend.repositories.db.Database.makeTransaction
import org.kotlinacademy.data.Puzzler
import org.kotlinacademy.fromJson
import org.kotlinacademy.now
import org.kotlinacademy.parseDateTime
import org.kotlinacademy.toJson

class PuzzlersDatabase : PuzzlersDatabaseRepository {

    override suspend fun getPuzzlers(): List<Puzzler> = makeTransaction {
        selectWholePuzzler()
                .execute()
                .map(::toPuzzler)
                .toList()
    }

    override suspend fun getAcceptedPuzzlers(): List<Puzzler> = makeTransaction {
        selectWholePuzzler()
                .where { PuzzlersTable.accepted eq true }
                .execute()
                .map(::toPuzzler)
                .toList()
    }

    override suspend fun getPuzzler(id: Int): Puzzler = makeTransaction {
        selectWholePuzzler()
                .where { PuzzlersTable.id eq id }
                .execute()
                .map(::toPuzzler)
                .toList()
                .first()
    }

    override suspend fun addPuzzler(puzzler: Puzzler, isAccepted: Boolean) = makeTransaction {
        insertInto(PuzzlersTable).values {
            it[id] = puzzler.id
            it[title] = puzzler.title
            it[question] = puzzler.question
            it[answers] = puzzler.answers
            it[author] = puzzler.author
            it[authorUrl] = puzzler.authorUrl
            it[dateTime] = puzzler.dateTime?.toDateFormatString()
            it[accepted] = isAccepted
        }.execute()
    }

    override suspend fun deletePuzzler(id: Int) = makeTransaction {
        deleteFrom(PuzzlersTable).where(PuzzlersTable.id eq id).execute()
    }

    override suspend fun updatePuzzler(id: Int, puzzler: Puzzler, isAccepted: Boolean?) = makeTransaction {
        require(countPuzzlersWithId(id) == 1) { "Should be single puzzler with id $id" }
        update(PuzzlersTable)
                .where { PuzzlersTable.id eq id }
                .set {
                    it[title] = puzzler.title
                    it[question] = puzzler.question
                    it[answers] = puzzler.answers
                    it[author] = puzzler.author
                    it[authorUrl] = puzzler.authorUrl
                    it[dateTime] = puzzler.dateTime?.toDateFormatString()
                    if(isAccepted != null) it[accepted] = isAccepted
                }.execute()
    }

    private fun selectWholePuzzler() = PuzzlersTable.select(PuzzlersTable.id, PuzzlersTable.title, PuzzlersTable.question, PuzzlersTable.answers, PuzzlersTable.author, PuzzlersTable.authorUrl)

    private fun toPuzzler(it: ResultRow) = Puzzler(
            id = it[PuzzlersTable.id],
            title = it[PuzzlersTable.title],
            question = it[PuzzlersTable.question],
            answers = it[PuzzlersTable.answers],
            author = it[PuzzlersTable.author],
            authorUrl = it[PuzzlersTable.authorUrl],
            dateTime = it[PuzzlersTable.dateTime].parseDateTime()
    )

    private fun Transaction.countPuzzlersWithId(id: Int) = PuzzlersTable.select(PuzzlersTable.id)
            .where { PuzzlersTable.id.eq(id) }
            .execute()
            .count()
}