package com.revolut.coroutines

import kotlin.coroutines.experimental.*
import kotlin.coroutines.experimental.intrinsics.*

import platform.darwin.*

class MainQueueDispatcher : ContinuationDispatcher() {

    override fun <T> dispatchResume(value: T, continuation: Continuation<T>): Boolean {
        println("MainQueueDispatcher resume")
        dispatch_async(dispatch_get_main_queue()) {
            continuation.resume(value)
        }
        return true
    }

    override fun dispatchResumeWithException(exception: Throwable, continuation: Continuation<*>): Boolean {
        dispatch_async(dispatch_get_main_queue()) {
            continuation.resumeWithException(exception)
        }
        return true
    }
}