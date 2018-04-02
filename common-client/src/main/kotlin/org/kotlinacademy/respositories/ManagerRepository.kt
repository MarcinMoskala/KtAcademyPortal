package org.kotlinacademy.respositories

import org.kotlinacademy.common.Provider
import org.kotlinacademy.data.NewsData

interface ManagerRepository {

    suspend fun getPropositions(secret: String): NewsData

    companion object : Provider<ManagerRepository>() {
        override fun create(): ManagerRepository = RepositoriesProvider.getManagerRepository()
    }
}