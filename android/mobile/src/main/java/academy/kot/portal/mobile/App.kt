package academy.kot.portal.mobile

import academy.kot.portal.android.makeInternetStatusInterceptor
import academy.kot.portal.android.makeUpdateNeededInterceptor
import academy.kot.portal.mobile.BuildConfig.VERSION_NAME
import android.app.Application
import com.crashlytics.android.Crashlytics
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import com.marcinmoskala.kotlinpreferences.gson.GsonSerializer
import io.fabric.sdk.android.Fabric
import okhttp3.Cache
import org.kotlinacademy.Headers
import org.kotlinacademy.gson
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
                    makeInternetStatusInterceptor(),
                    makeUpdateNeededInterceptor(Headers.androidMobileMinVersion, VERSION_NAME)
            )
        }
        PreferenceHolder.setContext(this)
        PreferenceHolder.serializer = GsonSerializer(gson)
        Fabric.with(this, Crashlytics())
    }


    companion object {
        var baseUrl: String? = null
    }
}