package org.kotlinacademy.mobile

import android.util.Log
import org.kotlinacademy.BuildConfig

fun Any.log(message: String) {
    if (BuildConfig.DEBUG) Log.e(this::class.simpleName, message)
}

fun Any.log(error: Throwable) {
    log(error.message ?: "Error", error)
}

fun Any.log(message: String, error: Throwable) {
    if (BuildConfig.DEBUG) Log.e(this::class.simpleName, message, error)
}