package com.marcinmoskala.kotlinacademy.desktop.view

import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.presentation.NewsPresenter
import com.marcinmoskala.kotlinacademy.presentation.NewsView
import javafx.application.Platform
import javafx.collections.FXCollections.observableArrayList
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*

class NewsTornadoView : View("Hello TornadoFX"), NewsView {

    override var loading: Boolean = false
    override var swipeRefresh: Boolean = false

    private val newsList = observableArrayList<News>()!!
    private val presenter by lazy { NewsPresenter(this) }

    override val root = listview(newsList) {
        prefWidth = 1000.0
        cellFormat { news ->
            graphic = hbox {
                imageview(news.imageUrl) {
                    padding = Insets(10.0)
                    fitHeight = 80.0
                    fitWidth = 80.0
                }
                vbox(alignment = Pos.CENTER_LEFT) {
                    padding = Insets(10.0)

                    text(news.title) {
                        font = Font.font(26.0)
                    }
                    text(news.subtitle) {
                        font = Font.font(18.0)
                    }
                    text(news.imageUrl) {
                        font = Font.font(12.0)
                    }
                }
                setOnMouseClicked {
                    val url = news.url ?: return@setOnMouseClicked
                    hostServices.showDocument(url)
                }
            }
        }

    }

    override fun onDock() {
        presenter.onCreate()
    }

    override fun showList(news: List<News>) {
        newsList.setAll(news)
    }

    override fun showError(throwable: Throwable) {
        Platform.runLater {
            error(throwable.message ?: "")
        }
    }
}