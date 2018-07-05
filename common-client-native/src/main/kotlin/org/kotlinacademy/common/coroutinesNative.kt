package org.kotlinacademy.common

import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.startCoroutine

actual suspend fun delay(time: Long): Unit {}

actual fun launch(context: CoroutineContext, block: suspend () -> Unit): Job {
    block.startCoroutine(EmptyContinuation(context))
    return NativeJob()
}

actual interface Job {
    actual val isCancelled: Boolean
    actual fun cancel(cause: Throwable?): Boolean
}

// TODO
class NativeJob : Job {
    override val isCancelled: Boolean = false
    override fun cancel(cause: Throwable?): Boolean = true
}

open class EmptyContinuation(override val context: CoroutineContext) : Continuation<Any?> {

    override fun resume(value: Any?) = Unit

    override fun resumeWithException(exception: Throwable) {
        throw exception
    }
}