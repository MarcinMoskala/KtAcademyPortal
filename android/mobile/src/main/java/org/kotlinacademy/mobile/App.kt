package org.kotlinacademy.mobile

import android.app.Application
import com.google.gson.Gson
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import com.marcinmoskala.kotlinpreferences.gson.GsonSerializer
import okhttp3.Cache
import org.kotlinacademy.Headers
import org.kotlinacademy.common.UI
import org.kotlinacademy.common.makeInternetStatusInterceptor
import org.kotlinacademy.common.makeUpdateNeededInterceptor
import org.kotlinacademy.mobile.BuildConfig.VERSION_NAME
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
                    makeInternetStatusInterceptor(),
                    makeUpdateNeededInterceptor(Headers.androidMobileMinVersion, VERSION_NAME)
            )
        }
        PreferenceHolder.setContext(this)
        PreferenceHolder.serializer = GsonSerializer(Gson())
    }


    companion object {
        var baseUrl: String? = null
    }
}