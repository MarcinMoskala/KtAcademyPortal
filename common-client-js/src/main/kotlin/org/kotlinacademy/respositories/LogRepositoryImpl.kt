package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints
import org.kotlinacademy.httpPost
import org.kotlinacademy.json

class LogRepositoryImpl : LogRepository {
    override suspend fun send(deviceType: String, userId: String, action: String, extra: String) {
        val data = """{
            "deviceType": "$deviceType",
            "userId": "$userId",
            "action": "$action",
            "extra": "$extra"
        }""".trimMargin()
        httpPost(data, Endpoints.log)
    }
}