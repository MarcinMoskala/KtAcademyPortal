package com.marcinmoskala.kotlinacademy.backend

import com.marcinmoskala.kotlinacademy.backend.api.MediumRepository
import com.marcinmoskala.kotlinacademy.backend.db.Database
import io.ktor.application.Application
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

fun Application.launchSyncJobs(database: Database) {
    val config = environment.config.config("medium")
    val intervalInMinutes = config.property("intervalInMinutes").getString().toLong()

    val mediumRepository = MediumRepository()

    launchEvery(intervalInMinutes, TimeUnit.MINUTES) {
        val news = mediumRepository.getNews()
        if (news == null) {
            println("Medium did not succeed when processing request")
            return@launchEvery
        }

        val prevNews = database.getNews()
        val newNews = news - prevNews
        newNews.forEach { database.updateOrAdd(it) }
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