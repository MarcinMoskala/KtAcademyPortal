package org.kotlinacademy.views

import kotlin.browser.document
import kotlin.js.Math

fun randomId(): String {
    val possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..10).map { possible[Math.floor(possible.length * Math.random())] }.fold("", { acc, c -> acc + c })
}

fun <T> valueOn(elementId: String): T? {
    val text = document.getElementById(elementId).asDynamic().value as String
    return text.takeUnless { it.isBlank() } as T
}

fun hideElementWithId(id: String) {
    document.getElementById(id)?.classList?.add("hidden")
}

fun showElementWithId(id: String) {
    document.getElementById(id)?.classList?.remove("hidden")
}