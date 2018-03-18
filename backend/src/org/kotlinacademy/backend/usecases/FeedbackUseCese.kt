package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.repositories.db.FeedbackDatabaseRepository
import org.kotlinacademy.data.Feedback

object FeedbackUseCese {

    suspend fun add(feedback: Feedback) {
        val feedbackDatabaseRepository = FeedbackDatabaseRepository.get()
        feedbackDatabaseRepository.addFeedback(feedback)
        EmailUseCase.sendInfoAboutFeedback(feedback)
    }

    suspend fun getAll(): List<Feedback> {
        val feedbackDatabaseRepository = FeedbackDatabaseRepository.get()
        return feedbackDatabaseRepository.getFeedback()
    }
}