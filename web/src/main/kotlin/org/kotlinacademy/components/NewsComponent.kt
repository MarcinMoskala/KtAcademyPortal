package org.kotlinacademy.components

import org.kotlinacademy.data.News
import org.kotlinacademy.presentation.news.NewsPresenter
import org.kotlinacademy.presentation.news.NewsView
import org.kotlinacademy.views.*
import react.RBuilder
import react.RProps
import react.ReactElement
import react.dom.div
import kotlin.properties.Delegates.observable

class NewsComponent : BaseComponent<RProps, NewsComponentState>(), NewsView {

    private val presenter by presenter { NewsPresenter(this) }

    override var loading: Boolean by observable(false) { _, _, n ->
        setState { state.loading = n }
    }
    override var refresh: Boolean by observable(false) { _, _, n ->
        setState { state.loading = n }
    }

    override fun RBuilder.render(): ReactElement? = when {
        state.loading != false -> loadingView()
        state.error != null -> errorView(state.error!!)
        state.newsList != null -> newsListView()
        else -> div { }
    }

    private fun RBuilder.newsListView(): ReactElement? = div(classes = "main") {
        headerView()
        newsListView(state.newsList)
        fabView()
    }

    override fun showList(news: List<News>) {
        setState { newsList = news }
    }
}

external interface NewsComponentState : BaseState {
    var loading: Boolean?
    var newsList: List<News>?
}