package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints.notification
import org.kotlinacademy.Endpoints.notificationRegister
import org.kotlinacademy.data.FirebaseTokenData
import org.kotlinacademy.data.FirebaseTokenType
import org.kotlinacademy.httpPost
import org.kotlinacademy.json

class NotificationRepositoryImpl : NotificationRepository {
    override suspend fun registerToken(token: String, type: FirebaseTokenType) {
        val body = json.stringify(FirebaseTokenData(token, type))
        httpPost("$notification/$notificationRegister", body)
    }
}