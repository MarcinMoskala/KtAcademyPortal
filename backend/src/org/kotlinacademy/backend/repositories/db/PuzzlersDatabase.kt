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

class PuzzlersDatabase : PuzzlersDatabaseRepository {

    override suspend fun getPuzzlers(): List<Puzzler> = makeTransaction {
        selectWholePuzzler()
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

    override suspend fun addPuzzler(puzzlerData: PuzzlerData, isAccepted: Boolean): Puzzler = makeTransaction {
        val id = insertInto(PuzzlersTable).values {
            it[title] = puzzlerData.title
            it[level] = puzzlerData.level
            it[actualQuestion] = puzzlerData.actualQuestion
            it[codeQuestion] = puzzlerData.codeQuestion
            it[answers] = puzzlerData.answers
            it[correctAnswer] = puzzlerData.correctAnswer
            it[explanation] = puzzlerData.explanation
            it[author] = puzzlerData.author
            it[authorUrl] = puzzlerData.authorUrl
            it[dateTime] = now.toDateFormatString()
            it[accepted] = isAccepted
        }.fetch(PuzzlersTable.id).execute()

        Puzzler(id, puzzlerData, now, false)
    }

    override suspend fun deletePuzzler(id: Int) = makeTransaction {
        deleteFrom(PuzzlersTable).where(PuzzlersTable.id eq id).execute()
    }

    override suspend fun updatePuzzler(puzzler: Puzzler) = makeTransaction {
        val id = puzzler.id
        require(countPuzzlersWithId(id) == 1) { "Should be single puzzler with id $id" }
        update(PuzzlersTable)
                .where { PuzzlersTable.id eq id }
                .set {
                    it[title] = puzzler.title
                    it[level] = puzzler.level
                    it[actualQuestion] = puzzler.actualQuestion
                    it[codeQuestion] = puzzler.codeQuestion
                    it[answers] = puzzler.answers
                    it[author] = puzzler.author
                    it[authorUrl] = puzzler.authorUrl
                    it[correctAnswer] = puzzler.correctAnswer
                    it[explanation] = puzzler.explanation
                    it[dateTime] = puzzler.dateTime.toDateFormatString()
                    it[accepted] = puzzler.accepted
                }.execute()
    }

    private fun selectWholePuzzler() = PuzzlersTable.select(PuzzlersTable.id, PuzzlersTable.title, PuzzlersTable.level, PuzzlersTable.actualQuestion, PuzzlersTable.codeQuestion, PuzzlersTable.answers, PuzzlersTable.author, PuzzlersTable.correctAnswer, PuzzlersTable.explanation, PuzzlersTable.authorUrl, PuzzlersTable.dateTime, PuzzlersTable.accepted)

    private fun toPuzzler(it: ResultRow) = Puzzler(
            id = it[PuzzlersTable.id],
            data = PuzzlerData(
                    title = it[PuzzlersTable.title],
                    level = it[PuzzlersTable.level],
                    actualQuestion = it[PuzzlersTable.actualQuestion],
                    codeQuestion = it[PuzzlersTable.codeQuestion],
                    answers = it[PuzzlersTable.answers],
                    correctAnswer = it[PuzzlersTable.correctAnswer],
                    explanation = it[PuzzlersTable.explanation],
                    author = it[PuzzlersTable.author],
                    authorUrl = it[PuzzlersTable.authorUrl]
            ),
            dateTime = it[PuzzlersTable.dateTime].parseDateTime(),
            accepted = it[PuzzlersTable.accepted]
    )

    private fun Transaction.countPuzzlersWithId(id: Int) = PuzzlersTable.select(PuzzlersTable.id)
            .where { PuzzlersTable.id.eq(id) }
            .execute()
            .count()
}