package com.marcinmoskala.kotlinacademy.respositories

import com.marcinmoskala.kotlinacademy.data.News
import kotlinx.coroutines.experimental.runBlocking
import retrofit2.Call
import retrofit2.HttpException
import ru.gildor.coroutines.retrofit.await

class NewsRepositoryImpl : NewsRepository {

    val api = retrofit.create(Api::class.java)

    override fun getNews(callback: (List<News>) -> Unit, onError: (Throwable) -> Unit, onFinish: () -> Unit) = runBlocking {
        try {
            val news = api.getNews().await()
            callback(news)
        } catch (e: HttpException) {
            onError(Error("Http ${e.code()} error: ${e.message()}"))
        } catch (e: Throwable) {
            onError(e)
        } finally {
            onFinish()
        }
    }

    interface Api {
        fun getNews(): Call<List<News>>
    }
}