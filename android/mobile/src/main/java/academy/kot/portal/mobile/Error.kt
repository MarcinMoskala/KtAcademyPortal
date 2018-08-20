package academy.kot.portal.mobile

import android.util.Log

fun Any.log(message: String) {
    if (BuildConfig.DEBUG) Log.e(this::class.simpleName, message)
}

fun Any.log(error: Throwable) {
    log(error.message ?: "Error", error)
}

fun Any.log(message: String, error: Throwable) {
    if (BuildConfig.DEBUG) Log.e(this::class.simpleName, message, error)
}