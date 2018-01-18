package org.kotlinacademy

actual fun <T> runBlocking(block: suspend () -> T): T = kotlinx.coroutines.experimental.runBlocking { block() }