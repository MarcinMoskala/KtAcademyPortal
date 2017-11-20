package com.marcinmoskala.kotlinacademy.common

import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.EmptyCoroutineContext
import kotlin.coroutines.experimental.startCoroutine
import kotlin.js.Promise

actual fun launchUI(block: suspend () -> Unit): Cancellable {
    async {
        block()
    }
    return object: Cancellable {} // TODO There should be also a way to cancel JS job
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

// TODO Find a way to suspend Kotlin/JS coroutine
actual suspend fun delay(time: Long) {
    require(time >= 0) { "Delay time $time cannot be negative" }
    if (time <= 0) return // don't delay
}