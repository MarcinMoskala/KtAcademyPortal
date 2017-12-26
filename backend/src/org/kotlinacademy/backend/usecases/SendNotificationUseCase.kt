package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.repositories.network.NotificationsRepository
import org.kotlinacademy.backend.repositories.network.dto.NotificationResult
import org.kotlinacademy.data.FirebaseTokenData
import org.kotlinacademy.data.FirebaseTokenType

suspend fun sendNotifications(text: String, url: String, databaseRepository: DatabaseRepository, notificationsRepository: NotificationsRepository, emailRepository: EmailRepository?) {
    val tokensData = getTokenData(databaseRepository)
    val generalNotificationResponse = tokensData.map { token -> sendNotification(text, url, token, notificationsRepository) }
            .reduce { acc, next -> NotificationResult(acc.success + next.success, acc.failure + next.failure) }
    if (emailRepository != null) {
        sendEmailWithNotificationResult(generalNotificationResponse, emailRepository)
    }
}

suspend fun sendNotification(text: String, url: String, tokenData: FirebaseTokenData, notificationsRepository: NotificationsRepository): NotificationResult {
    val (token, type) = tokenData
    val title = "Kotlin Academy"
    val icon = when (type) {
        FirebaseTokenType.Android -> "icon_notification"
        FirebaseTokenType.Web -> "img/logo.png"
    }
    return notificationsRepository.sendNotification(title, text, icon, url, token)
}