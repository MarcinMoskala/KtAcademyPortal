package org.kotlinacademy.presentation.notifications

interface RegisterNotificationTokenView {
    fun setTokenRegistered(token: String)
    fun logError(error: Throwable)
}