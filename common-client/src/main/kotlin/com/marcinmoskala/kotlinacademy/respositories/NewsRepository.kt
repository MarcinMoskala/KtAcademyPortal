package com.marcinmoskala.kotlinacademy.respositories

import com.marcinmoskala.kotlinacademy.common.Provider
import com.marcinmoskala.kotlinacademy.data.NewsData

interface NewsRepository {

    suspend fun getNewsData(): NewsData

    companion object : Provider<NewsRepository>() {
        override fun create(): NewsRepository = RepositoriesProvider.getNewsRepository()
    }
}