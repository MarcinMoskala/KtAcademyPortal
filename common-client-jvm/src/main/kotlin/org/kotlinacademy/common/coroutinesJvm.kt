package org.kotlinacademy.common

import java.util.concurrent.TimeUnit

actual suspend fun delay(time: Long): Unit = kotlinx.coroutines.experimental.delay(time, TimeUnit.MILLISECONDS)