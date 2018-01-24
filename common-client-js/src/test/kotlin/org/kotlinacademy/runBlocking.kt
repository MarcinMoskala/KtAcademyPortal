package org.kotlinacademy

actual fun <T> runBlocking(block: suspend () -> T) {
    // JS runs on single thread so such implementation doesn't make sense.
    // For now, we will not test this kind of functions on JS.
    // no-op
}