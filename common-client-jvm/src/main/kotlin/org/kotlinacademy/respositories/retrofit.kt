package org.kotlinacademy.respositories

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.kotlinacademy.gson
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

var BaseURL: String by Delegates.notNull()

val retrofit by lazy { makeRetrofit() }

private fun makeRetrofit(vararg interceptors: okhttp3.Interceptor) = retrofit2.Retrofit.Builder()
        .baseUrl(BaseURL)
        .client(makeHttpClient(interceptors))
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()!!

private fun makeHttpClient(interceptors: Array<out okhttp3.Interceptor>) = okhttp3.OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(headersInterceptor())
        .addInterceptor(loggingInterceptor())
        .apply { for (i in interceptors) addInterceptor(i) }
        .build()

fun headersInterceptor() = Interceptor { chain ->
    chain.proceed(chain.request().newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Accept-Language", "en")
            .addHeader("Content-Type", "application/json")
            .build())
}


fun loggingInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}