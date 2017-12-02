package com.marcinmoskala.kotlinacademy.ui

import android.app.Application
import com.marcinmoskala.kotlinacademy.BuildConfig
import com.marcinmoskala.kotlinacademy.common.UI
import com.marcinmoskala.kotlinacademy.respositories.BaseURL
import kotlinx.coroutines.experimental.android.UI as AndroidUI

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        UI = AndroidUI
        BaseURL = BuildConfig.SERVER_URL
    }
}