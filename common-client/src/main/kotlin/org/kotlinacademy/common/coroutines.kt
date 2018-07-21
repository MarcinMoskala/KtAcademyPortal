package org.kotlinacademy.common

import kotlin.coroutines.experimental.CoroutineContext

expect fun launch(context: CoroutineContext, block: suspend () -> Unit): Job

expect suspend fun delay(time: Long)

expect interface Job {
    val isCancelled: Boolean
    fun cancel(cause: Throwable?): Boolean
}