package org.kotlinacademy.desktop

import kotlinx.coroutines.experimental.javafx.JavaFx
import org.kotlinacademy.desktop.views.TornadoNewsView
import org.kotlinacademy.respositories.makeRetrofit
import org.kotlinacademy.respositories.retrofit
import tornadofx.App

class MyApp : App(TornadoNewsView::class, Styles::class) {
    init {
        retrofit = makeRetrofit("https://kotlin-academy.herokuapp.com/")
    }
}