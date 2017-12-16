package org.kotlinacademy.desktop

import javafx.scene.image.Image
import javafx.stage.Stage
import kotlinx.coroutines.experimental.javafx.JavaFx
import org.kotlinacademy.common.UI
import org.kotlinacademy.desktop.views.TornadoNewsView
import org.kotlinacademy.respositories.BaseURL
import tornadofx.*

class MyApp : App(TornadoNewsView::class, Styles::class) {
    init {
        UI = JavaFx
        BaseURL = "https://kotlin-academy.herokuapp.com/"
    }
}