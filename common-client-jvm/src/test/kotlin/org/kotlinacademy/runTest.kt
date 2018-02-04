package org.kotlinacademy

import kotlinx.coroutines.experimental.runBlocking

actual fun <T> runTest(block: suspend () -> T) {
    runBlocking { block() }
}