package org.kotlinacademy.respositories

import org.kotlinacademy.common.Provider
import org.kotlinacademy.data.PuzzlerData

interface LogRepository {

    suspend fun send(deviceType: String, userId: String, action: String, extra: String)

    companion object : Provider<LogRepository>() {
        override fun create(): LogRepository = RepositoriesProvider.getLogRepository()
    }
}