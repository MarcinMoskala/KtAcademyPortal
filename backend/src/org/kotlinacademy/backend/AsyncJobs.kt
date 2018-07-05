package org.kotlinacademy.backend

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.kotlinacademy.backend.usecases.JobsUseCase
import org.kotlinacademy.backend.usecases.MediumUseCase
import org.kotlinacademy.backend.usecases.NewsUseCase
import java.util.*
import java.util.Calendar.*
import java.util.concurrent.TimeUnit

fun launchSyncJobs() {
    val interval = Config.mediumRefreshIntervalInMinutes ?: return
    launchEvery(interval, TimeUnit.MINUTES) {
        MediumUseCase.sync()
    }
    doAt(THURSDAY, hour = 17..17) {
        MediumUseCase.proposePostWithLastWeekPuzzlers()
    }
    doAt(TUESDAY, THURSDAY, SATURDAY, hour = 12..15) {
        JobsUseCase.fillDayWithPuzzler()
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

private fun doAt(vararg weekday: Int, hour: IntRange, block: suspend CoroutineScope.() -> Unit) {
    launch(CommonPool) {
        while (true) {
            val now = getInstance(TimeZone.getTimeZone("Poland"))
            if (now.get(DAY_OF_WEEK) in weekday && now.get(HOUR_OF_DAY) in hour) {
                block()
            }
            delay(1, TimeUnit.HOURS)
        }
    }
}