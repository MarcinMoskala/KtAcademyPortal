package com.marcinmoskala.kotlinacademy.backend.dto

class PushNotificationData(
        val to: String,
        val notification: NotificationData
)

class NotificationData(
        val title: String,
        val body: String
)