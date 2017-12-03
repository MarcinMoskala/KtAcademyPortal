package com.marcinmoskala.kotlinacademy.backend.usecases

import com.marcinmoskala.kotlinacademy.backend.repositories.db.DatabaseRepository
import com.marcinmoskala.kotlinacademy.backend.repositories.network.NotificationsRepository

suspend fun addToken(token: String, tokenType: TokenType, databaseRepository: DatabaseRepository, notificationRepository: NotificationsRepository?) {
    databaseRepository.addToken(token, tokenType)
    notificationRepository?.sendNotification("Thank you for registration :)", token)
}

suspend fun getTokens(tokenType: TokenType, databaseRepository: DatabaseRepository)
        = databaseRepository.getTokens(tokenType)

enum class TokenType {
    Web,
    Android
}