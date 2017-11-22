package com.marcinmoskala.kotlinacademy.components

import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.presentation.news.NewsPresenter
import com.marcinmoskala.kotlinacademy.presentation.news.NewsView
import com.marcinmoskala.kotlinacademy.views.errorView
import com.marcinmoskala.kotlinacademy.views.headerView
import com.marcinmoskala.kotlinacademy.views.loadingView
import com.marcinmoskala.kotlinacademy.views.newsListView
import react.*
import react.dom.div
import kotlin.properties.Delegates.observable

class NewsComponent : RComponent<RProps, MainState>(), NewsView {

    private val presenter by lazy { NewsPresenter(this) }

    override var loading: Boolean by observable(false) { p, o, n ->
        setState { state.loading = n }
    }
    override var swipeRefresh: Boolean by observable(false) { p, o, n ->
        setState { state.loading = n }
    }

    override fun RBuilder.render(): ReactElement? = when {
        state.loading != false -> loadingView()
        state.error != null -> errorView(state.error!!)
        state.newsList != null -> mainView()
        else -> div { }
    }

    private fun RBuilder.mainView(): ReactElement? = div(classes = "main") {
        headerView()
        newsListView(state.newsList)
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

    override fun showError(error: Throwable) {
        setState { this.error = error }
    }
}

external interface MainState : RState {
    var loading: Boolean?
    var newsList: List<News>?
    var error: Throwable?
}