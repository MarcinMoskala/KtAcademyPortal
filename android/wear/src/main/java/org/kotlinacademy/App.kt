package org.kotlinacademy

import android.app.Application
import org.kotlinacademy.common.UI
import org.kotlinacademy.mobile.setUpServer
import org.kotlinacademy.respositories.makeRetrofit
import org.kotlinacademy.respositories.retrofit
import kotlinx.coroutines.experimental.android.UI as AndroidUI

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        UI = AndroidUI
        setUpServer(serverCreator = { baseUrl ->
            retrofit = makeRetrofit(baseUrl)
        })
    }
}