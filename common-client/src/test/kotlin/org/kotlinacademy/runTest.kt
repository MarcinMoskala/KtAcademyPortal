package org.kotlinacademy

import kotlin.coroutines.experimental.CoroutineContext

expect fun <T> runTest(block: suspend () -> T)

expect val Unconfined: CoroutineContext
