package org.kotlinacademy.backend.repositories.db

import org.kotlinacademy.backend.common.Provider

interface LogDatabaseRepository {

    suspend fun add(deviceType: String, userId: String, action: String, extra: String)

    companion object: Provider<LogDatabaseRepository>() {
        override fun create(): LogDatabaseRepository = Database.logDatabase
    }
}