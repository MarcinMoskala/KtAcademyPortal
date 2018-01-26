package org.kotlinacademy

import kotlinx.coroutines.experimental.promise

actual fun <T> runTest(block: suspend () -> T): dynamic = promise { block() }