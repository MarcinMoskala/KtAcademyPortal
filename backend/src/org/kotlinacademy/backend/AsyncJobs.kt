package org.kotlinacademy.backend

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.kotlinacademy.backend.usecases.MediumUseCase
import org.kotlinacademy.backend.usecases.NewsUseCase
import java.util.concurrent.TimeUnit

fun launchSyncJobs() {
    val interval = Config.mediumRefreshIntervalInMinutes ?: return
    val schedule = getSchedule()
    launchEvery(interval, TimeUnit.MINUTES) {
        MediumUseCase.sync()
        NewsUseCase.publishScheduled(schedule)
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