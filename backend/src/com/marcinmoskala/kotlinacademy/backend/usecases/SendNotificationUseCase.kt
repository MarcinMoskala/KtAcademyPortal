package com.marcinmoskala.kotlinacademy.backend.usecases

import com.marcinmoskala.kotlinacademy.backend.repositories.db.DatabaseRepository
import com.marcinmoskala.kotlinacademy.backend.repositories.network.NotificationsRepository

suspend fun sendNotification(text: String, databaseRepository: DatabaseRepository, notificationsRepository: NotificationsRepository) {
    val tokens = databaseRepository.getWebTokens()
    for (token in tokens) {
        notificationsRepository.sendNotification(text, token)
    }
}