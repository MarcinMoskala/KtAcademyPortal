package org.kotlinacademy

import kotlinx.coroutines.experimental.runBlocking
import kotlin.coroutines.experimental.CoroutineContext

actual fun <T> runTest(block: suspend () -> T) {
    runBlocking { block() }
}

actual val Unconfined: CoroutineContext = kotlinx.coroutines.experimental.Unconfined