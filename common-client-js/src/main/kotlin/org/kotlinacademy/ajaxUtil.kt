package org.kotlinacademy

import org.w3c.xhr.XMLHttpRequest
import kotlin.coroutines.experimental.suspendCoroutine

suspend fun httpGet(url: String): String = http("GET", url)

suspend fun httpPost(body: String, url: String): String = http("POST", url, body, "Content-Type" to "application/json")

private suspend inline fun <reified T : Any> http(method: String, url: String, body: String? = null, vararg headers: Pair<String, String>): T = suspendCoroutine { c ->
    val xhr = XMLHttpRequest()
    xhr.onreadystatechange = {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            if (xhr.status / 100 == 2) {
                c.resume(xhr.response as T)
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