package org.kotlinacademy.respositories

import kotlinx.serialization.json.JSON
import org.kotlinacademy.Endpoints
import org.kotlinacademy.data.FirebaseTokenData
import org.kotlinacademy.data.FirebaseTokenType
import org.kotlinacademy.httpPost

class NotificationsRepositoryImpl(val json: JSON) : NotificationRepository {
    suspend override fun registerToken(token: String, type: FirebaseTokenType) {
        val body = json.stringify(FirebaseTokenData(token, type))
        val url = "${Endpoints.notification}/${Endpoints.notificationRegister}"
        httpPost(body, url)
    }
}