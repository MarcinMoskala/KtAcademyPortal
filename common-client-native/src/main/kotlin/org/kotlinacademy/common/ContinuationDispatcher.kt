package com.revolut.coroutines

import kotlin.coroutines.experimental.*
import kotlin.coroutines.experimental.intrinsics.*

import platform.darwin.*

abstract class ContinuationDispatcher :
        AbstractCoroutineContextElement(ContinuationInterceptor), ContinuationInterceptor {

    abstract fun <T> dispatchResume(value: T, continuation: Continuation<T>): Boolean
    abstract fun dispatchResumeWithException(exception: Throwable, continuation: Continuation<*>): Boolean
    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return DispatchedContinuation(this, continuation)
    }

}

internal class DispatchedContinuation<T>(
        private val dispatcher: ContinuationDispatcher,
        private val continuation: Continuation<T>
) : Continuation<T> {

    override val context: CoroutineContext = continuation.context

    override fun resume(value: T) {
        if (dispatcher.dispatchResume(value, continuation).not()) {
            continuation.resume(value)
        }
    }

    override fun resumeWithException(exception: Throwable) {
        if (dispatcher.dispatchResumeWithException(exception, continuation).not()) {
            continuation.resumeWithException(exception)
        }
    }
}

