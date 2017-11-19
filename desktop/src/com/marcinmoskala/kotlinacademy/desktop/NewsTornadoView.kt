package com.marcinmoskala.kotlinacademy.desktop

import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.presentation.NewsPresenter
import com.marcinmoskala.kotlinacademy.presentation.NewsView
import javafx.collections.FXCollections.observableArrayList
import tornadofx.View
import tornadofx.column
import tornadofx.smartResize
import tornadofx.tableview

class NewsTornadoView : View("Hello TornadoFX"), NewsView {

    override var loading: Boolean = false
    override var swipeRefresh: Boolean = false

    private val newsList = observableArrayList<News>()!!
    private val presenter by lazy { NewsPresenter(this) }

    override val root = tableview(newsList) {
        column("Title", News::title)
        column("Description", News::subtitle)
        smartResize()
    }

    override fun onCreate() {
        super.onCreate()
        presenter.onCreate()
    }

    override fun showList(news: List<News>) {
        newsList.setAll(news)
    }

    override fun showError(throwable: Throwable) {

    }
}