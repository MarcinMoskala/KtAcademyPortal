package org.kotlinacademy.respositories

import org.kotlinacademy.data.FirebaseTokenType

interface NotificationRepository {

    suspend fun registerToken(token: String, type: FirebaseTokenType)
}