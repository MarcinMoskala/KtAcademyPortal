package org.kotlinacademy.common

import android.content.Context
import okhttp3.CacheControl
import okhttp3.Interceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

fun makeInternetStatusInterceptor(context: Context) = Interceptor { chain ->
    if (context.isOffline()) {
        throw NoInternetConnectionError()
    }
    chain.proceed(chain.request())
}

fun makeResponseOfflineCacheInterceptor(context: Context) = Interceptor { chain ->
    var request = chain.request()
    if (context.isOffline()) {
        val cacheControl = CacheControl.Builder()
                .maxStale(7, TimeUnit.DAYS)
                .build()
        request = request.newBuilder()
                .cacheControl(cacheControl)
                .build()
    }
    chain.proceed(request)
}

fun Context.isOffline(): Boolean {
    val connected = this.isConnected != false
    val airplaneModeOn = this.isAirplaneModeOn
    return !connected || airplaneModeOn
}

fun makeUpdateNeededInterceptor(flagName: String, version: String) = Interceptor { chain ->
    val response = chain.proceed(chain.request())
    val minVersion = response.headers(flagName).firstOrNull()
    if (minVersion != null && minVersion.decodeVersion() > version.decodeVersion()) {
        throw UnsupportedVersionError()
    }
    response
}

private fun String.decodeVersion(): Int {
    val (major, minor, build) = this.split(".").map(String::toInt)
    return major * 10_000 + minor * 100 + build
}

class NoInternetConnectionError : IOException("No internet connection")
class UnsupportedVersionError : IOException("Unsupported version, update required")