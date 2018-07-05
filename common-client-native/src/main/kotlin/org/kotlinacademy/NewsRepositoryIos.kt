package org.kotlinacademy

import kotlin.coroutines.experimental.*
import kotlin.coroutines.experimental.intrinsics.*
import org.kotlinacademy.*
import org.kotlinacademy.presentation.*
import org.kotlinacademy.data.*
import org.kotlinacademy.respositories.NewsRepository

abstract class NewsRepositoryIos: NewsRepository {

    abstract fun getNewsDataCallback(callback: Continuation<NewsData>)

    override suspend fun getNewsData(): NewsData = suspendCoroutineOrReturn { continuation ->
        getNewsDataCallback(continuation)
        COROUTINE_SUSPENDED
    }
}