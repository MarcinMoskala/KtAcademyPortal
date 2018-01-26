package org.kotlinacademy

actual fun <T> runTest(block: suspend () -> T) {
    kotlinx.coroutines.experimental.runBlocking { block() }
}