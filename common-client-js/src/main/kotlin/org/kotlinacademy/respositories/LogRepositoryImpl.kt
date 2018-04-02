package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints
import org.kotlinacademy.httpPost

class LogRepositoryImpl : LogRepository {
    override suspend fun send(deviceType: String, userId: String, action: String, extra: String) {
        httpPost(Endpoints.log, """{
            "deviceType": "$deviceType",
            "userId": "$userId",
            "action": "$action",
            "extra": "$extra"
        }""".trimMargin())
    }
}