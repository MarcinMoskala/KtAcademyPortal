package org.kotlinacademy

import org.kotlinacademy.common.async

actual fun <T> runBlocking(block: suspend () -> T): T {
    return kotlinx.coroutines.experimental.runBlocking { block() }
}