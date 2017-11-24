package com.marcinmoskala.kotlinacademy.components

import com.marcinmoskala.kotlinacademy.common.routeLink
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.presentation.news.NewsPresenter
import com.marcinmoskala.kotlinacademy.presentation.news.NewsView
import com.marcinmoskala.kotlinacademy.views.*
import react.*
import react.dom.div
import kotlin.properties.Delegates.observable

class NewsComponent : BaseComponent<RProps, NewsComponentState>(), NewsView {

    private val presenter by presenter { NewsPresenter(this) }

    override var loading: Boolean by observable(false) { _, _, n ->
        setState { state.loading = n }
    }
    override var swipeRefresh: Boolean by observable(false) { _, _, n ->
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

    override fun showError(error: Throwable) {
        setState { this.error = error }
    }
}

external interface NewsComponentState : BaseState {
    var loading: Boolean?
    var newsList: List<News>?
}