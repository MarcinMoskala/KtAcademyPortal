package com.marcinmoskala.kotlinacademy.components

import com.marcinmoskala.kotlinacademy.utils.bindToStateProperty
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.presentation.news.NewsPresenter
import com.marcinmoskala.kotlinacademy.presentation.news.NewsView
import react.*
import react.dom.a
import react.dom.div
import react.dom.h2
import react.dom.h3
import kotlin.js.Console
import kotlin.math.log

class NewsComponent : RComponent<RProps, MainState>(), NewsView {

    private val presenter by lazy { NewsPresenter(this) }

    override var loading: Boolean by bindToStateProperty(state::loading)
    override var swipeRefresh: Boolean by bindToStateProperty(state::swipeRefresh)

    override fun RBuilder.render(): ReactElement? = when {
        state.loading == true || state.swipeRefresh == true -> loadingView()
        state.error != null -> errorView()
        state.newsList != null -> newsListView()
        else -> div { }
    }

    private fun RBuilder.loadingView(): ReactElement? = div(classes = "loading") {
        +"Loading data..."
    }

    private fun RBuilder.errorView(): ReactElement? = div(classes = "error") {
        +state.error.orEmpty()
    }

    private fun RBuilder.newsListView(): ReactElement? = div(classes = "news-list") {
        state.newsList?.forEach { news ->
            a(classes = "news default-font", href = news.url) {
                div(classes = "news-card") {
                    div(classes = "news-frame") {
                        h3(classes = "news-title") { +news.title }
                        div(classes = "news-subtitle") {
                            +news.subtitle
                        }
                    }
                }
            }
        }
    }

    override fun componentDidMount() {
        presenter.onCreate()
    }

    override fun componentWillUnmount() {
        presenter.onDestroy()
    }

    override fun showList(news: List<News>) {
        setState { newsList = news }
    }

    override fun showError(throwable: Throwable) {
        setState { error = throwable.message }
    }
}

external interface MainState : RState {
    var loading: Boolean?
    var swipeRefresh: Boolean?
    var newsList: List<News>?
    var error: String?
}