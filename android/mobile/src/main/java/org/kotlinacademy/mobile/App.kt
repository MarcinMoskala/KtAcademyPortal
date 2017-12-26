package org.kotlinacademy.mobile

import android.app.Application
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import org.kotlinacademy.common.UI
import org.kotlinacademy.mobile.view.notifications.FirebaseIdService
import kotlinx.coroutines.experimental.android.UI as AndroidUI

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        UI = AndroidUI
        setUpServer()
        PreferenceHolder.setContext(this)
        FirebaseIdService.ensureThatTokenSent()
    }
}