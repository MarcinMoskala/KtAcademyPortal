package org.kotlinacademy

import org.w3c.xhr.XMLHttpRequest
import kotlin.coroutines.experimental.suspendCoroutine

suspend fun httpGet(url: String): String = http("GET", url)

suspend fun httpPost(url: String, body: String = ""): String = http("POST", url, body, "Content-Type" to "application/json")

private suspend fun http(method: String, url: String, body: String? = null, vararg headers: Pair<String, String>): String = suspendCoroutine { c ->
    val xhr = XMLHttpRequest()
    xhr.onreadystatechange = {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            if (xhr.status in 200..299) {
                val resp = xhr.response as String
                c.resume(resp)
            } else {
                c.resumeWithException(Throwable("HTTP error: ${xhr.status}"))
            }
        }
        null
    }
    xhr.open(method, url)
    for ((key, value) in headers)
        xhr.setRequestHeader(key, value)

    if (body == null) xhr.send() else xhr.send(body)
}