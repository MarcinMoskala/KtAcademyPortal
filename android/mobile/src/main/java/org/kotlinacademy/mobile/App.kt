package org.kotlinacademy.mobile

import android.app.Application
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import okhttp3.Cache
import org.kotlinacademy.Headers
import org.kotlinacademy.common.UI
import org.kotlinacademy.common.makeInternetStatusInterceptor
import org.kotlinacademy.common.makeResponseOfflineCacheInterceptor
import org.kotlinacademy.common.makeUpdateNeededInterceptor
import org.kotlinacademy.mobile.BuildConfig.*
import org.kotlinacademy.mobile.view.notifications.FirebaseIdService
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
                    makeInternetStatusInterceptor(this),
                    makeUpdateNeededInterceptor(Headers.androidMobileMinVersion, VERSION_NAME)
            )
        }
        PreferenceHolder.setContext(this)
        FirebaseIdService.ensureThatTokenSent()
    }


    companion object {
        var baseUrl: String? = null
    }
}