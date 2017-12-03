package com.marcinmoskala.kotlinacademy.backend.usecases

import com.marcinmoskala.kotlinacademy.backend.repositories.db.DatabaseRepository
import com.marcinmoskala.kotlinacademy.data.Feedback

suspend fun addFeedback(feedback: Feedback, databaseRepository: DatabaseRepository) {
    databaseRepository.addFeedback(feedback)
}

suspend fun getAllFeedback(databaseRepository: DatabaseRepository)
        = databaseRepository.getFeedback()