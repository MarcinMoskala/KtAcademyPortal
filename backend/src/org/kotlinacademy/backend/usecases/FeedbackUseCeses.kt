package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.data.Feedback

suspend fun addFeedback(feedback: Feedback, emailRepository: EmailRepository?, databaseRepository: DatabaseRepository) {
    databaseRepository.addFeedback(feedback)
    if (emailRepository != null) {
        sendEmailWithInfoAboutFeedback(feedback, emailRepository, databaseRepository)
    }
}

suspend fun getAllFeedback(databaseRepository: DatabaseRepository)
        = databaseRepository.getFeedback()