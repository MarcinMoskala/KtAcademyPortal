package academy.kot.portal.wear

import android.app.Application
import okhttp3.Cache
import org.kotlinacademy.Headers
import academy.kot.portal.android.makeInternetStatusInterceptor
import academy.kot.portal.android.makeResponseOfflineCacheInterceptor
import academy.kot.portal.android.makeUpdateNeededInterceptor
import academy.kot.portal.wear.BuildConfig.VERSION_NAME
import org.kotlinacademy.respositories.makeRetrofit
import org.kotlinacademy.respositories.retrofit

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setUpBaseUrlOrMock()
        baseUrl?.let { baseUrl ->
            val cacheSize: Long = 10 * 1024 * 1024 // 10 MB
            val cache = Cache(cacheDir, cacheSize)
            retrofit = makeRetrofit(baseUrl, cache,
                    makeResponseOfflineCacheInterceptor(this),  // Need to be first interceptor!
                    makeInternetStatusInterceptor(),
                    makeUpdateNeededInterceptor(Headers.androidWearMinVersion, VERSION_NAME)
            )
        }
    }

    companion object {
        var baseUrl: String? = null
    }
}