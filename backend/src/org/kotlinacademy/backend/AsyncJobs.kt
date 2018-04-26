package org.kotlinacademy.backend

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.kotlinacademy.backend.usecases.MediumUseCase
import org.kotlinacademy.backend.usecases.NewsUseCase
import java.util.*
import java.util.concurrent.TimeUnit

fun launchSyncJobs() {
    val interval = Config.mediumRefreshIntervalInMinutes ?: return
    launchEvery(interval, TimeUnit.MINUTES) {
        MediumUseCase.sync()
    }
    doOnceAWeek(Calendar.THURSDAY, 17) {
        MediumUseCase.proposePostWithLastWeekPuzzlers()
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

private fun doOnceAWeek(weekday: Int, hour: Int, block: suspend CoroutineScope.() -> Unit) {
    launch(CommonPool) {
        while (true) {
            val now = Calendar.getInstance(TimeZone.getTimeZone("Poland"))
            if(now.get(Calendar.DAY_OF_WEEK) == weekday && now.get(Calendar.HOUR_OF_DAY) == hour) {
                block()
            }
            delay(1, TimeUnit.HOURS)
        }
    }
}