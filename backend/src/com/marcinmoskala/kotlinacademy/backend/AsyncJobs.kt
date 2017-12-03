package com.marcinmoskala.kotlinacademy.backend

import com.marcinmoskala.kotlinacademy.backend.repositories.db.DatabaseRepository
import com.marcinmoskala.kotlinacademy.backend.repositories.network.MediumRepository
import com.marcinmoskala.kotlinacademy.backend.repositories.network.NotificationsRepository
import com.marcinmoskala.kotlinacademy.backend.usecases.syncWithMedium
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

fun launchSyncJobs() {
    val mediumRepository by MediumRepository.lazyGet()
    val databaseRepository by DatabaseRepository.lazyGet()
    val notificationsRepository by NotificationsRepository.lazyGet()

    launchEvery(Config.mediumRefreshIntervalInMinutes, TimeUnit.MINUTES) {
        syncWithMedium(mediumRepository, databaseRepository, notificationsRepository)
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