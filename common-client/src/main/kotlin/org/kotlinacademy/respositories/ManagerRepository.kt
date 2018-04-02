package org.kotlinacademy.respositories

import org.kotlinacademy.common.Provider
import org.kotlinacademy.data.NewsData

interface ManagerRepository {

    suspend fun getPropositions(secret: String): NewsData
    suspend fun acceptInfo(id: Int, secret: String)
    suspend fun acceptPuzzler(id: Int, secret: String)
    suspend fun rejectInfo(id: Int, secret: String)
    suspend fun rejectPuzzler(id: Int, secret: String)

    companion object : Provider<ManagerRepository>() {
        override fun create(): ManagerRepository = RepositoriesProvider.getManagerRepository()
    }
}