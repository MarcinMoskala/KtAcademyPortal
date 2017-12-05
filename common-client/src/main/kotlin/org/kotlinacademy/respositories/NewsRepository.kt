package org.kotlinacademy.respositories

import org.kotlinacademy.common.Provider
import org.kotlinacademy.data.NewsData

interface NewsRepository {

    suspend fun getNewsData(): NewsData

    companion object : Provider<NewsRepository>() {
        override fun create(): NewsRepository = RepositoriesProvider.getNewsRepository()
    }
}