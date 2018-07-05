package org.kotlinacademy

import kotlinx.coroutines.experimental.promise
import kotlin.coroutines.experimental.CoroutineContext

actual fun <T> runTest(block: suspend () -> T): dynamic = promise { block() }

actual val Unconfined: CoroutineContext = kotlinx.coroutines.experimental.Unconfined