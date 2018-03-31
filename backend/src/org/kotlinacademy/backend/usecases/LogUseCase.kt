package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.repositories.db.LogDatabaseRepository

object LogUseCase {

    suspend fun add(deviceType: String, userId: String, action: String, extra: String) {
        val logDatabaseRepository = LogDatabaseRepository.get()

        logDatabaseRepository.add(deviceType, userId, action, extra)
    }
}