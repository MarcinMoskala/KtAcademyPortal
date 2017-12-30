package org.kotlinacademy

import org.w3c.xhr.XMLHttpRequest
import kotlin.coroutines.experimental.suspendCoroutine

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

suspend fun httpPost(body: String, url: String): String = suspendCoroutine { c ->
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
    xhr.open("POST", url)
    xhr.setRequestHeader("Content-Type", "application/json")
    xhr.send(body)
}