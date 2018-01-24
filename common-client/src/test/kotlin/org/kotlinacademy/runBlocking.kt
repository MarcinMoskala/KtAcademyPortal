package org.kotlinacademy

expect fun <T> runBlocking(block: suspend () -> T)