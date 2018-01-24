package org.kotlinacademy.common

import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.EmptyCoroutineContext
import kotlin.coroutines.experimental.startCoroutine
import kotlin.js.Promise

actual fun launchUI(block: suspend () -> Unit): Cancellable {
    async {
        block()
    }
    return object : Cancellable {} // TODO There should be also a way to cancel JS job
}

fun <T> async(x: suspend () -> T): Promise<T> = Promise { resolve, reject ->
    x.startCoroutine(object : Continuation<T> {
        override val context = EmptyCoroutineContext

        override fun resume(value: T) {
            resolve(value)
        }

        override fun resumeWithException(exception: Throwable) {
            reject(exception)
        }
    })
}

actual suspend fun delay(time: Long): Unit = kotlinx.coroutines.experimental.delay(time.toInt())

external fun setTimeout(function: () -> Unit, delay: Long)