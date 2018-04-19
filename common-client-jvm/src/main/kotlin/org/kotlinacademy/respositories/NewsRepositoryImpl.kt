package org.kotlinacademy.respositories

import kotlinx.coroutines.experimental.Deferred
import org.kotlinacademy.Endpoints.news
import org.kotlinacademy.common.HttpError
import org.kotlinacademy.data.NewsData
import retrofit2.HttpException
import retrofit2.http.GET

class NewsRepositoryImpl : NewsRepository {

    private val api = retrofit.create(Api::class.java)!!

    override suspend fun getNewsData(): NewsData = try {
        api.getNews().await()
    } catch (t: HttpException) {
        throw HttpError(t.code(), t.message())
    } catch (t: Throwable) {
        throw t
    }

    interface Api {

        @GET(news)
        fun getNews(): Deferred<NewsData>
    }
}