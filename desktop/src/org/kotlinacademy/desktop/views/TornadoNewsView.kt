package org.kotlinacademy.desktop.views

import javafx.animation.Interpolator
import javafx.animation.Timeline
import javafx.beans.property.SimpleBooleanProperty
import javafx.collections.FXCollections.observableArrayList
import javafx.geometry.Pos
import javafx.geometry.Pos.BOTTOM_RIGHT
import javafx.scene.control.Button
import javafx.scene.paint.Color
import org.kotlinacademy.data.Article
import org.kotlinacademy.data.News
import org.kotlinacademy.data.url
import org.kotlinacademy.desktop.Styles
import org.kotlinacademy.presentation.news.NewsPresenter
import org.kotlinacademy.presentation.news.NewsView
import org.kotlinacademy.respositories.NewsRepositoryImpl
import tornadofx.*


class TornadoNewsView : BaseTornadoView("Kotlin Academy"), NewsView {
    private val loadingProperty = SimpleBooleanProperty()
    override var loading by loadingProperty

    private val refreshProperty = SimpleBooleanProperty()
    override var refresh by refreshProperty

    private val newsList = observableArrayList<Article>()!!

    private val newsRepository = NewsRepositoryImpl()
    private val presenter = NewsPresenter(this, newsRepository)

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
                    onUserSelect { if (it is Article) hostServices.showDocument(it.url) }
                }
                progressbar {
                    removeWhen(loadingProperty.not())
                }
                button {
                    graphic = svgicon(Styles.replyIcon)
                    addClass(Styles.icon, Styles.generalFeedback)
                    stackpaneConstraints { alignment = BOTTOM_RIGHT }
                    tooltip("Click to leave a comment")
                    action { find<FeedbackForm>(Scope()).openModal() }
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
        newsList.setAll(news.filterIsInstance<Article>())
    }
}