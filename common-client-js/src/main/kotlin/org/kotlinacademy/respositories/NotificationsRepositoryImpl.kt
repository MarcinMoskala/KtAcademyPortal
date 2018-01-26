package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints
import org.kotlinacademy.data.FirebaseTokenData
import org.kotlinacademy.data.FirebaseTokenType
import org.kotlinacademy.httpPost
import org.kotlinacademy.json

class NotificationsRepositoryImpl : NotificationRepository {
    override suspend fun registerToken(token: String, type: FirebaseTokenType) {
        val body = json.stringify(FirebaseTokenData(token, type))
        val url = "${Endpoints.notification}/${Endpoints.notificationRegister}"
        httpPost(body, url)
    }
}