package org.kotlinacademy.common

import kotlinx.coroutines.experimental.Unconfined
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.suspendCoroutine
import kotlin.properties.Delegates.notNull

// Should be set for different platforms
var UI: CoroutineContext = Unconfined

actual fun launchUI(block: suspend () -> Unit): Cancellable {
    val job = kotlinx.coroutines.experimental.launch(UI) {
        block()
    }
    return object: Cancellable {
        override fun cancel() {
            if (job.isActive) job.cancel()
        }
    }
}

private val executor = Executors.newSingleThreadScheduledExecutor {
    Thread(it, "scheduler").apply { isDaemon = true }
}

actual suspend fun delay(time: Long): Unit = suspendCoroutine { cont ->
    executor.schedule({ cont.resume(Unit) }, time, TimeUnit.MILLISECONDS)
}