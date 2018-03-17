package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.repositories.db.ArticlesDatabaseRepository
import org.kotlinacademy.backend.repositories.db.FeedbackDatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.data.Feedback

object FeedbackUseCese {

    suspend fun add(feedback: Feedback, emailRepository: EmailRepository?, articlesDatabaseRepository: ArticlesDatabaseRepository, feedbackDatabaseRepository: FeedbackDatabaseRepository) {
        feedbackDatabaseRepository.addFeedback(feedback)
        if (emailRepository != null) {
            EmailUseCase.sendInfoAboutFeedback(feedback, emailRepository, articlesDatabaseRepository)
        }
    }

    suspend fun getAll(databaseRepository: FeedbackDatabaseRepository) = databaseRepository.getFeedback()
}