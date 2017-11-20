package com.marcinmoskala.kotlinacademy.components

import com.marcinmoskala.kotlinacademy.utils.bindToStateProperty
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.presentation.news.NewsPresenter
import com.marcinmoskala.kotlinacademy.presentation.news.NewsView
import react.*
import react.dom.div

class NewsComponent : RComponent<RProps, MainState>(), NewsView {

    private val presenter by lazy { NewsPresenter(this) }

    override var loading: Boolean by bindToStateProperty(state::loading)
    override var swipeRefresh: Boolean = false

    override fun RBuilder.render(): ReactElement? = div(classes = "sessions") {
        if (state.loading) {
            div(classes = "loading") {
                +"Loading data..."
            }
        } else {
            state.sessionsError?.let {
                div { +it }
            }

            val newsList = state.newsList ?: listOf()
            newsList.forEach { news ->
                div(classes = "news") {
                    div(classes = "news-title") {
                        +news.title
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
        setState { sessionsError = throwable.message }
    }
}

external interface MainState : RState {
    var loading: Boolean
    var newsList: List<News>?
    var sessionsError: String?
}