package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.backend.repositories.network.NotificationsRepository
import org.kotlinacademy.data.FirebaseTokenData
import org.kotlinacademy.data.FirebaseTokenType

suspend fun sendNotifications(text: String, databaseRepository: DatabaseRepository, notificationsRepository: NotificationsRepository) {
    val tokensData = getTokenData(databaseRepository)
    for (tokenData in tokensData) {
        sendNotification(text, tokenData, notificationsRepository)
    }
}

suspend fun sendNotification(text: String, tokenData: FirebaseTokenData, notificationsRepository: NotificationsRepository) {
    val (token, type) = tokenData
    val icon = when (type) {
        FirebaseTokenType.Android -> "icon_notification"
        FirebaseTokenType.Web -> "img/logo.png"
    }
    notificationsRepository.sendNotification(text, icon, token)
}