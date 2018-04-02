package org.kotlinacademy.common

import kotlin.browser.window

fun getUrlVars(): MutableMap<String, String> {
    val vars = mutableMapOf<String, String>()
    window.location.href.replace("""[?&]+([^=&]+)=([^&]*)""".toRegex(), {
        val key: String = it.groupValues[1]
        val value: String = it.groupValues[2]
        vars[key] = value
        ""
    })
    return vars
}

fun getUrlParam(parameter: String): String? {
    if (window.location.href.indexOf(parameter) > -1) {
        val text = getUrlVars()[parameter]
        return encodeURIComponent(text.orEmpty()).takeUnless { it.isBlank() }
    }
    return null
}

val secretInUrl: String?
    get() = getUrlParam("secret")

val baseUrl: String
    get() = window.location.origin + window.location.pathname


external fun encodeURIComponent(uri: String): String

fun goBack() {
    js("history.back()")
}