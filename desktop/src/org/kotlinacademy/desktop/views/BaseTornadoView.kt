package org.kotlinacademy.desktop.views

import org.kotlinacademy.presentation.BaseView
import javafx.scene.Node
import tornadofx.*

abstract class BaseTornadoView(title: String? = null, icon: Node? = null) : View(title, icon), BaseView {

    override fun showError(error: Throwable) = throw(error)

    override fun logError(error: Throwable) {
        error.printStackTrace()
    }
}