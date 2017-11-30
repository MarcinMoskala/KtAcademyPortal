package com.marcinmoskala.kotlinacademy.desktop

import com.marcinmoskala.kotlinacademy.common.UI
import com.marcinmoskala.kotlinacademy.desktop.views.TornadoNewsView
import com.marcinmoskala.kotlinacademy.respositories.BaseURL
import kotlinx.coroutines.experimental.javafx.JavaFx
import tornadofx.*

class MyApp : App(TornadoNewsView::class, Styles::class) {
    init {
        UI = JavaFx
        BaseURL = "https://kotlin-academy.herokuapp.com/"
    }
}