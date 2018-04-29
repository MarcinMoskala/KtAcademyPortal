package org.kotlinacademy.backend.repositories.network.notifications

class PushNotificationData(
        val to: String,
        val notification: NotificationData
)

data class NotificationData(
        val title: String,
        val body: String,
        val icon: String,
        val click_action: String? = null
)

class NotificationResult(
        val success: Int,
        val failure: Int
)