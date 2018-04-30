package org.kotlinacademy.common

import kotlinx.coroutines.experimental.launch
import org.kotlinacademy.kebabCase
import org.kotlinacademy.respositories.LogRepositoryImpl
import org.kotlinacademy.views.randomId
import kotlin.browser.window

private external fun ga(gaAction: String, hitType: String, category: String, eventAction: String, label: String)

private const val userIdKey = "userRandomId"

private val logRepository by lazy { LogRepositoryImpl() }

fun getUserId(): String {
    val storedId = window.localStorage.getItem(userIdKey)
    if (storedId != null && storedId.isNotBlank()) {
        return storedId
    }
    val newId = randomId()
    window.localStorage.setItem(userIdKey, newId)
    return newId
}

fun sendEvent(category: String, action: String, label: String = "") = launch {
    val userId = getUserId()
    ga("send", "event", category, action, "$label $userId")
    logRepository.send("web", userId, "$category-${action.kebabCase()}", label)
}