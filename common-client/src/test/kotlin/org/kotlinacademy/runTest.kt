package org.kotlinacademy

expect fun <T> runTest(block: suspend () -> T)