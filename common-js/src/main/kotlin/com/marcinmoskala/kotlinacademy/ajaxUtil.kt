package com.marcinmoskala.kotlinacademy

import org.w3c.xhr.XMLHttpRequest
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.EmptyCoroutineContext
import kotlin.coroutines.experimental.startCoroutine
import kotlin.coroutines.experimental.suspendCoroutine
import kotlin.js.Promise

suspend fun httpGet(url: String): String = suspendCoroutine { c ->
    val xhr = XMLHttpRequest()
    xhr.onreadystatechange = {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            if (xhr.status / 100 == 2) {
                c.resume(xhr.response as String)
            } else {
                c.resumeWithException(Throwable("HTTP error: ${xhr.status}"))
            }
        }
        null
    }
    xhr.open("GET", url)
    xhr.send()
}

fun <T> async(x: suspend () -> T): Promise<T> = Promise { resolve, reject ->
    x.startCoroutine(object : Continuation<T> {
        override val context = EmptyCoroutineContext

        override fun resume(value: T) {
            resolve(value)
        }

        override fun resumeWithException(exception: Throwable) {
            reject(exception)
        }
    })
}