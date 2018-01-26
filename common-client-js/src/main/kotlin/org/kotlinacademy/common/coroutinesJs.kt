package org.kotlinacademy.common

actual suspend fun delay(time: Long): Unit = kotlinx.coroutines.experimental.delay(time.toInt())

external fun setTimeout(function: () -> Unit, delay: Long)