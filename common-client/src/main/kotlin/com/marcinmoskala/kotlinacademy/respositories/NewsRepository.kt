package com.marcinmoskala.kotlinacademy.respositories

import com.marcinmoskala.kotlinacademy.common.Provider
import com.marcinmoskala.kotlinacademy.data.News

interface NewsRepository {

    fun getNews(callback: (List<News>)->Unit, onError: (Throwable)->Unit, onFinish: ()->Unit)

    companion object : Provider<NewsRepository>() {
        override fun create(): NewsRepository = RepositoriesProvider.getNewsRepository()
    }
}