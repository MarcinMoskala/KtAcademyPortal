package com.marcinmoskala.kotlinacademy.respositories

import com.marcinmoskala.kotlinacademy.common.Provider
import com.marcinmoskala.kotlinacademy.data.News

interface NewsRepository {

    suspend fun getNews(): List<News>

    companion object : Provider<NewsRepository>() {
        override fun create(): NewsRepository = RepositoriesProvider.getNewsRepository()
    }
}