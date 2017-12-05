package com.marcinmoskala.kotlinacademy.desktop.views

import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.desktop.Styles
import com.marcinmoskala.kotlinacademy.presentation.news.NewsPresenter
import com.marcinmoskala.kotlinacademy.presentation.news.NewsView
import javafx.animation.Interpolator
import javafx.animation.Interpolator.LINEAR
import javafx.animation.Timeline
import javafx.beans.property.SimpleBooleanProperty
import javafx.collections.FXCollections.observableArrayList
import javafx.geometry.Pos
import javafx.geometry.Pos.BOTTOM_RIGHT
import javafx.scene.control.Button
import javafx.scene.paint.Color
import tornadofx.*


class TornadoNewsView : View("Kotlin Academy"), NewsView {
    private val loadingProperty = SimpleBooleanProperty()
    override var loading by loadingProperty

    private val refreshProperty = SimpleBooleanProperty()
    override var refresh by refreshProperty

    private val newsList = observableArrayList<News>()!!
    private val presenter = NewsPresenter(this)

    override val root = borderpane {
        addClass(Styles.newsView)
        top {
            stackpane {
                addClass(Styles.header)
                vbox {
                    addClass(Styles.listCenter)
                    label(title).addClass(Styles.headerTitle)
                    label("With mission to simplify Kotlin learning")
                }
                button {
                    enableWhen(refreshProperty.not())
                    graphic = svgicon(Styles.refreshIcon, 20, Color.WHITE)
                    stackpaneConstraints { alignment = Pos.TOP_LEFT }
                    addClass(Styles.icon)
                    action(presenter::onRefresh)
                    rotateWhenRefreshing()
                    tooltip("Click to refresh the news list")
                }
            }
        }
        center {
            stackpane {
                listview(newsList) {
                    removeWhen(loadingProperty)
                    addClass(Styles.newsList)
                    cellFragment(NewsListFragment::class)
                    onUserSelect { hostServices.showDocument(it.url) }
                }
                progressbar {
                    removeWhen(loadingProperty.not())
                }
                button {
                    graphic = svgicon(Styles.replyIcon)
                    addClass(Styles.icon, Styles.generalFeedback)
                    stackpaneConstraints { alignment = BOTTOM_RIGHT }
                    tooltip("Click to leave a comment")
                    action { find<CommentForm>(Scope()).openModal() }
                }
            }
        }
    }

    private fun Button.rotateWhenRefreshing() {
        val rotation = rotate(0.3.seconds, 360, Interpolator.LINEAR, play = false) {
            cycleCount = Timeline.INDEFINITE
            isAutoReverse = true
        }

        refreshProperty.onChange {
            if (it) {
                rotation.playFromStart()
            } else {
                rotation.stop()
                rotate = 0.0
            }
        }
    }

    override fun onDock() {
        presenter.onCreate()
    }

    override fun showList(news: List<News>) {
        newsList.setAll(news)
    }

    // Let TornadoFX show the error
    override fun showError(error: Throwable) = throw error

    override fun logError(error: Throwable) {
        error.printStackTrace()
    }
}