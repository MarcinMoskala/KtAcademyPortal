package com.marcinmoskala.kotlinacademy.desktop.view

import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.presentation.news.NewsPresenter
import com.marcinmoskala.kotlinacademy.presentation.news.NewsView
import javafx.application.Platform
import javafx.collections.FXCollections.observableArrayList
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.text.Font
import tornadofx.*
import com.github.plushaze.traynotification.notification.TrayNotification
import com.github.plushaze.traynotification.notification.Notifications


class NewsTornadoView : View("Hello TornadoFX"), NewsView {

    override var loading: Boolean = false
    override var refresh: Boolean = false

    private val newsList = observableArrayList<News>()!!
    private val presenter by lazy { NewsPresenter(this) }

    override val root = vbox {
        prefWidth = 540.0

        datagrid(newsList) {
            cellWidth = 500.0
            cellHeight = 300.0

            cellCache { news ->
                val cell = this
                onMouseClicked = EventHandler<MouseEvent> {
                    val url = news.url ?: return@EventHandler
                    hostServices.showDocument(url)
                }
                vbox(alignment = Pos.CENTER) {
                    padding = Insets(10.0)
                    imageview("http://i.imgur.com/o2QoeNnb.jpg") {
                        padding = Insets(10.0)
                        fitHeight = 200.0
//                        centerImage()
                        isPreserveRatio = true
                    }
                    text(news.title) {
                        alignment = Pos.CENTER_LEFT
                        font = Font.font(26.0)
                        wrappingWidthProperty().bind(cell.widthProperty())
                    }
                    text(news.subtitle) {
                        alignment = Pos.CENTER_LEFT
                        padding = Insets(10.0)
                        font = Font.font(18.0)
                        wrappingWidthProperty().bind(cell.widthProperty())
                    }
                }
            }
        }
    }

    private fun ImageView.centerImage() {
        val img = image ?: return
        val ratioX = fitWidth / img.getWidth()
        val ratioY = fitHeight / img.getHeight()
        val reducCoeff = minOf(ratioY, ratioX)
        x = (fitWidth - img.width * reducCoeff) / 2
        y = (fitHeight - img.height * reducCoeff) / 2
    }

    override fun onDock() {
        TrayNotification().apply {
            title = "Congratulations sir"
            message = "You've successfully created your first Tray Notification"
            notification = Notifications.SUCCESS
        }.showAndWait()

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