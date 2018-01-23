package org.kotlinacademy.backend.repositories.network

import org.kotlinacademy.backend.repositories.network.dto.MediumPostsResponse
import org.kotlinacademy.backend.repositories.network.dto.toNews
import org.kotlinacademy.common.Provider
import org.kotlinacademy.data.News
import org.kotlinacademy.fromJson
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import ru.gildor.coroutines.retrofit.await

interface MediumRepository {

    suspend fun getNews(): List<News>?

    class MediumRepositoryImpl : MediumRepository {
        private val api: Api = makeRetrofit("https://medium.com/kotlin-academy/").create(Api::class.java)

        override suspend fun getNews(): List<News>? =
                api.getPlainResponse()
                        .await()
                        // Needed because of Medium API policy https://github.com/Medium/medium-api-docs/issues/115
                        .dropWhile { it != '{' }
                        .fromJson<MediumPostsResponse>()
                        .let { if (it != null && it.success) it.toNews() else null }
    }

    interface Api {

        @Headers("Accept: application/json")
        @GET("latest?count=1000")
        fun getPlainResponse(): Call<String>
    }

    companion object : Provider<MediumRepository>() {
        override fun create(): MediumRepository = MediumRepositoryImpl()
    }
}