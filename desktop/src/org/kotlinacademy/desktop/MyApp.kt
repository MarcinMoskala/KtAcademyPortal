package org.kotlinacademy.desktop

import org.kotlinacademy.common.UI
import org.kotlinacademy.desktop.views.TornadoNewsView
import org.kotlinacademy.respositories.BaseURL
import kotlinx.coroutines.experimental.javafx.JavaFx
import tornadofx.*

class MyApp : App(TornadoNewsView::class, Styles::class) {
    init {
        UI = JavaFx
        BaseURL = "https://kotlin-academy.herokuapp.com/"
    }
}