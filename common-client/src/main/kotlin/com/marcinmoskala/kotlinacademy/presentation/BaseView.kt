package com.marcinmoskala.kotlinacademy.presentation

interface BaseView {
    fun logError(error: Throwable)
    fun showError(error: Throwable)
}