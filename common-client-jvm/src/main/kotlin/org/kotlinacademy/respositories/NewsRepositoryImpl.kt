package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints
import org.kotlinacademy.Endpoints.news
import org.kotlinacademy.common.HttpError
import org.kotlinacademy.data.News
import org.kotlinacademy.data.NewsData
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.http.GET
import ru.gildor.coroutines.retrofit.await

class NewsRepositoryImpl : NewsRepository {

    private val api = retrofit.create(Api::class.java)!!

    override suspend fun getNewsData(): NewsData = try {
        api.getNews().await()
    } catch (t: HttpException) {
        throw HttpError(t.code(), t.message())
    }

    interface Api {

        @GET(news)
        fun getNews(): Call<NewsData>
    }
}