package org.kotlinacademy.views

import org.w3c.dom.Element
import kotlin.browser.document
import kotlin.js.Math

fun randomId(): String {
    val possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..10).map { possible[Math.floor(possible.length * Math.random())] }.fold("", { acc, c -> acc + c })
}

@Suppress("UNCHECKED_CAST")
fun <T> valueOn(elementId: String): T? {
    val text = document.getElementById(elementId).asDynamic().value as String
    return text.takeUnless { it.isBlank() } as T
}

fun getById(id: String) = document.getElementById(id)

fun Element.hide() {
    this.classList.add("hidden")
}

fun Element.show() {
    this.classList.remove("hidden")
}

var Element.src: Any
    get() = asDynamic().src
    set(v) {
        asDynamic().src = v
    }