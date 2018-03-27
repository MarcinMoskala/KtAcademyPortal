package org.kotlinacademy.common

private external fun ga(gaAction: String, hitType: String, category: String, eventAction: String, label: String)

fun sendEvent(category: String, action: String, label: String) {
    ga("send", "event", category, action, label)
}