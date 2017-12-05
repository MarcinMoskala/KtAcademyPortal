package org.kotlinacademy.respositories

import org.kotlinacademy.common.Provider
import org.kotlinacademy.data.FirebaseTokenType

interface NotificationRepository {

    suspend fun registerToken(token: String, type: FirebaseTokenType)

    companion object : Provider<NotificationRepository>() {
        override fun create(): NotificationRepository = RepositoriesProvider.getNotificationRepository()
    }
}