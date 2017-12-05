package org.kotlinacademy.backend.repositories.network.dto

class PushNotificationData(
        val to: String,
        val notification: NotificationData
)

class NotificationData(
        val title: String,
        val icon: String
)