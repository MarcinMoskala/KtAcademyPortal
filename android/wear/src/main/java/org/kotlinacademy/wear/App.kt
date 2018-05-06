package org.kotlinacademy.wear

import android.app.Application
import okhttp3.Cache
import org.kotlinacademy.Headers
import org.kotlinacademy.common.UI
import org.kotlinacademy.common.makeInternetStatusInterceptor
import org.kotlinacademy.common.makeResponseOfflineCacheInterceptor
import org.kotlinacademy.common.makeUpdateNeededInterceptor
import org.kotlinacademy.respositories.makeRetrofit
import org.kotlinacademy.respositories.retrofit
import kotlinx.coroutines.experimental.android.UI as AndroidUI

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        UI = AndroidUI
        setUpBaseUrlOrMock()
        baseUrl?.let { baseUrl ->
            val cacheSize: Long = 10 * 1024 * 1024 // 10 MB
            val cache = Cache(cacheDir, cacheSize)
            retrofit = makeRetrofit(baseUrl, cache,
                    makeResponseOfflineCacheInterceptor(this),  // Need to be first interceptor!
                    makeInternetStatusInterceptor(),
                    makeUpdateNeededInterceptor(Headers.androidWearMinVersion, BuildConfig.VERSION_NAME)
            )
        }
    }

    companion object {
        var baseUrl: String? = null
    }
}