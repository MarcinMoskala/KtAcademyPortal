package org.kotlinacademy.common

import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

actual suspend fun delay(time: Long): Unit = kotlinx.coroutines.experimental.delay(time.toInt())

external fun setTimeout(function: () -> Unit, delay: Long)

actual fun launch(context: CoroutineContext, block: suspend () -> Unit): Job = launch(context) { block() }

actual typealias Job = kotlinx.coroutines.experimental.Job