package org.kotlinacademy.mobile

import android.app.Application
import org.kotlinacademy.common.UI
import kotlinx.coroutines.experimental.android.UI as AndroidUI

class App: Application() {

    private val serverConfig = ServerConfig()

    override fun onCreate() {
        super.onCreate()
        UI = AndroidUI
        serverConfig.setUp()
    }
}