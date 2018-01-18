package org.kotlinacademy

actual fun <T> runBlocking(block: suspend () -> T): T {
    return kotlinx.coroutines.experimental.runBlocking { block() }
}