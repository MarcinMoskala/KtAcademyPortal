package com.marcinmoskala.kotlinacademy.desktop

import com.marcinmoskala.kotlinacademy.common.UI
import com.marcinmoskala.kotlinacademy.desktop.view.NewsTornadoView
import com.marcinmoskala.kotlinacademy.respositories.BaseURL
import kotlinx.coroutines.experimental.DefaultDispatcher
import tornadofx.App

class MyApp : App(NewsTornadoView::class, Styles::class) {
    init {
        UI = DefaultDispatcher
        BaseURL = "http://localhost:8080/"
    }
}