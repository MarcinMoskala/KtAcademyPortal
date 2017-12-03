package com.marcinmoskala.kotlinacademy.backend.repositories.network

import com.marcinmoskala.kotlinacademy.backend.repositories.network.dto.MediumPostsResponse
import com.marcinmoskala.kotlinacademy.backend.repositories.network.dto.toNews
import com.marcinmoskala.kotlinacademy.common.Provider
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.fromJson
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import ru.gildor.coroutines.retrofit.await

interface MediumRepository {

    suspend fun getNews(): List<News>?

    class MediumRepositoryImpl() : MediumRepository {
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
        @GET("latest")
        fun getPlainResponse(): Call<String>
    }

    companion object : Provider<MediumRepository>() {
        override fun create(): MediumRepository = MediumRepositoryImpl()
    }
}