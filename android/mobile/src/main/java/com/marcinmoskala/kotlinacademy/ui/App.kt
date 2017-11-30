package com.marcinmoskala.kotlinacademy.ui

import android.app.Application
import com.marcinmoskala.kotlinacademy.common.UI
import com.marcinmoskala.kotlinacademy.respositories.BaseURL
import kotlinx.coroutines.experimental.android.UI as AndroidUI

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        UI = AndroidUI
        BaseURL = "http://10.0.2.2:8080/"
    }
}