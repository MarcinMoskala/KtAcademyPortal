package com.marcinmoskala.kotlinacademy.respositories

import com.marcinmoskala.kotlinacademy.common.Provider
import com.marcinmoskala.kotlinacademy.data.FirebaseTokenType

interface NotificationRepository {

    suspend fun registerToken(token: String, type: FirebaseTokenType)

    companion object : Provider<NotificationRepository>() {
        override fun create(): NotificationRepository = RepositoriesProvider.getNotificationRepository()
    }
}