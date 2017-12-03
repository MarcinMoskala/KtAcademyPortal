package com.marcinmoskala.kotlinacademy.backend.usecases

import com.marcinmoskala.kotlinacademy.backend.repositories.db.DatabaseRepository
import com.marcinmoskala.kotlinacademy.backend.repositories.network.NotificationsRepository
import com.marcinmoskala.kotlinacademy.data.FirebaseTokenData
import com.marcinmoskala.kotlinacademy.data.FirebaseTokenType

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