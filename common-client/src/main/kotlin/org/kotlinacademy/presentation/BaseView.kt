package org.kotlinacademy.presentation

interface BaseView {
    fun logError(error: Throwable)
    fun showError(error: Throwable)
}