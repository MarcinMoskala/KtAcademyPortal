package org.kotlinacademy

import android.app.Application
import org.kotlinacademy.common.UI
import org.kotlinacademy.mobile.setUpServer
import kotlinx.coroutines.experimental.android.UI as AndroidUI

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        UI = AndroidUI
        setUpServer()
    }
}