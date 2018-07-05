package org.kotlinacademy.common

import android.content.Context
import okhttp3.CacheControl
import okhttp3.Interceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

fun Context.makeInternetStatusInterceptor() = Interceptor { chain ->
    if (isOffline()) {
        throw NoInternetConnectionError()
    }
    chain.proceed(chain.request())
}

fun makeResponseOfflineCacheInterceptor(context: Context) = Interceptor { chain ->
    var request = chain.request()
    if (context.isOffline()) {
        val cacheControl = CacheControl.Builder()
                .maxStale(14, TimeUnit.DAYS)
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
    val minVersionCode = minVersion?.decodeVersion()
    val versionCode = version.decodeVersion() ?: throw IOException("Version code is not correct")
    if (minVersionCode != null && minVersionCode > versionCode) {
        throw UnsupportedVersionError()
    }
    response
}

private fun String.decodeVersion(): Int? {
    val splitted = this.split(".")
    if (splitted.size < 3) return null
    val (major, minor, build) = splitted.map(String::toInt)
    return major * 10_000 + minor * 100 + build
}

class NoInternetConnectionError : IOException("No internet connection")
class UnsupportedVersionError : IOException("Unsupported version, update required")