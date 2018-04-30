package org.kotlinacademy.respositories

interface LogRepository {

    suspend fun send(deviceType: String, userId: String, action: String, extra: String)
}