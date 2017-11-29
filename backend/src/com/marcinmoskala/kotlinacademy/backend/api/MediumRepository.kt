package com.marcinmoskala.kotlinacademy.backend.api

import com.google.gson.Gson
import com.marcinmoskala.kotlinacademy.Endpoints.news
import com.marcinmoskala.kotlinacademy.backend.data.MediumPostsResponse
import com.marcinmoskala.kotlinacademy.backend.data.toNews
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.fromJson
import com.marcinmoskala.kotlinacademy.gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import ru.gildor.coroutines.retrofit.await
import java.util.concurrent.TimeUnit

class MediumRepository {

    private val api: Api = makeRetrofit().create(Api::class.java)

    suspend fun getNews(): List<News>? =
        api.getPlainResponse()
            .await()
            // TODO Dirty hack because of Medium API error https://github.com/Medium/medium-api-docs/issues/115
            .dropWhile { it != '{' }
            .fromJson<MediumPostsResponse>()
            .let { if(it != null && it.success) it.toNews() else null }

    interface Api {

        @Headers("Accept: application/json")
        @GET("latest")
        fun getMediumPosts(): Call<MediumPostsResponse>

        @Headers("Accept: application/json")
        @GET("latest")
        fun getPlainResponse(): Call<String>
    }

    private fun makeRetrofit() = Retrofit.Builder()
            .baseUrl("https://medium.com/kotlin-academy/")
            .client(makeHttpClient())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    private fun makeHttpClient() = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor())
            .build()

    private fun loggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}