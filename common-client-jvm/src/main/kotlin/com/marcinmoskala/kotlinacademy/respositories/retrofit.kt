package com.marcinmoskala.kotlinacademy.respositories

import com.google.gson.Gson
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val retrofit by lazy { makeRetrofit() }

private fun makeRetrofit(vararg interceptors: okhttp3.Interceptor) = retrofit2.Retrofit.Builder()
        .baseUrl("https://localhost/")
        .client(makeHttpClient(interceptors))
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()!!

private fun makeHttpClient(interceptors: Array<out okhttp3.Interceptor>) = okhttp3.OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor())
        .apply { for (i in interceptors) addInterceptor(i) }
        .build()

fun loggingInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}