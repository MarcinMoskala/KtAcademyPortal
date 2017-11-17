package com.marcinmoskala.kotlinacademy.respositories

import com.marcinmoskala.kotlinacademy.Endpoints
import com.marcinmoskala.kotlinacademy.async
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.httpGet

class NewsRepositoryImpl : NewsRepository {
    override fun getNews(callback: (List<News>) -> Unit, onError: (Throwable) -> Unit, onFinish: () -> Unit) {
        async {
            try {
                val rawData = httpGet(Endpoints.getNews)
                callback(JSON.parse(rawData))
            } catch (t: Throwable) {
                onError(t)
            } finally {
                onFinish()
            }
        }
    }
}