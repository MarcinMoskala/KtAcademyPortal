package org.kotlinacademy.common

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

private val executor = Executors.newSingleThreadScheduledExecutor {
    Thread(it, "scheduler").apply { isDaemon = true }
}

actual suspend fun delay(time: Long): Unit = kotlinx.coroutines.experimental.delay(time, TimeUnit.MILLISECONDS)