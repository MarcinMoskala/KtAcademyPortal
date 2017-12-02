package com.marcinmoskala.kotlinacademy.backend

import com.marcinmoskala.kotlinacademy.backend.api.MediumRepository
import com.marcinmoskala.kotlinacademy.backend.db.DatabaseRepository
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

fun launchSyncJobs() {
    mediumNewsChecher()
}

private fun mediumNewsChecher() {
    val mediumRepository by MediumRepository.lazyGet()
    val databaseRepository by DatabaseRepository.lazyGet()

    launchEvery(Config.mediumRefreshIntervalInMinutes, TimeUnit.MINUTES) {
        val news = mediumRepository.getNews()
        if (news == null) {
            logInfo("Medium did not succeed when processing request")
            return@launchEvery
        }

        val prevNewsTitles = databaseRepository.getNews().map { it.title }
        val newNews = news.filter { it.title !in prevNewsTitles }
        newNews.forEach { databaseRepository.addOrReplaceNews(it) }
    }
}

private fun launchEvery(interval: Long, unit: TimeUnit, block: suspend CoroutineScope.() -> Unit) {
    launch(CommonPool) {
        while (true) {
            block()
            delay(interval, unit)
        }
    }
}