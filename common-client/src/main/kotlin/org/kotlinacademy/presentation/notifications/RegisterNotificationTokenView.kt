package org.kotlinacademy.presentation.notifications

interface RegisterNotificationTokenView {
    fun setTokenRegistered()
    fun logError(error: Throwable)
}