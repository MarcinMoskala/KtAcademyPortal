package org.kotlinacademy.backend.repositories.db

import org.jetbrains.squash.query.select
import org.jetbrains.squash.results.get
import org.jetbrains.squash.statements.insertInto
import org.jetbrains.squash.statements.values
import org.kotlinacademy.backend.repositories.db.Database.makeTransaction
import org.kotlinacademy.data.Feedback

class FeedbackDatabase : FeedbackDatabaseRepository {

    override suspend fun getFeedback(): List<Feedback> = makeTransaction {
        FeedbackTable.select(FeedbackTable.newsId, FeedbackTable.rating, FeedbackTable.commentText, FeedbackTable.suggestionsText)
                .execute()
                .distinct()
                .map { Feedback(it[FeedbackTable.newsId], it[FeedbackTable.rating], it[FeedbackTable.commentText], it[FeedbackTable.suggestionsText]) }
                .toList()
    }

    override suspend fun addFeedback(feedback: Feedback) = makeTransaction {
        insertInto(FeedbackTable).values {
            it[newsId] = feedback.newsId
            it[rating] = feedback.rating
            it[commentText] = feedback.comment
            it[suggestionsText] = feedback.suggestions
        }.execute()
    }
}