package org.kotlinacademy.common

import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit
import kotlin.coroutines.experimental.CoroutineContext

actual suspend fun delay(time: Long): Unit = kotlinx.coroutines.experimental.delay(time, TimeUnit.MILLISECONDS)

external fun setTimeout(function: () -> Unit, delay: Long)

actual fun launch(context: CoroutineContext, block: suspend () -> Unit): Job = launch(UI) { block() }

actual typealias Job = kotlinx.coroutines.experimental.Job