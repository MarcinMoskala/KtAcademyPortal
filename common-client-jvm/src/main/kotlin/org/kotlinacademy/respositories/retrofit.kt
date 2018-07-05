package org.kotlinacademy.respositories

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.kotlinacademy.gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

lateinit var retrofit: Retrofit

fun makeRetrofit(baseUrl: String, cache: Cache? = null, vararg interceptors: okhttp3.Interceptor) = retrofit2.Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(makeHttpClient(cache, interceptors))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()!!

private fun makeHttpClient(cache: Cache?, interceptors: Array<out okhttp3.Interceptor>) = okhttp3.OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .let { if (cache != null) it.cache(cache) else it }
        .apply { for (i in interceptors) addInterceptor(i) }
        .addInterceptor(headersInterceptor())
        .addInterceptor(loggingInterceptor())
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