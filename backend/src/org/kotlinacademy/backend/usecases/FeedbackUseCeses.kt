package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.data.Feedback

suspend fun addFeedback(feedback: Feedback, databaseRepository: DatabaseRepository) {
    databaseRepository.addFeedback(feedback)
}

suspend fun getAllFeedback(databaseRepository: DatabaseRepository)
        = databaseRepository.getFeedback()