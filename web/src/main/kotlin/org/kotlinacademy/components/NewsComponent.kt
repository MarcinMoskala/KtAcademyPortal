package org.kotlinacademy.components

import org.kotlinacademy.data.Article
import org.kotlinacademy.data.Info
import org.kotlinacademy.data.News
import org.kotlinacademy.data.Puzzler
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
        state.news != null -> newsListView()
        else -> div { }
    }

    private fun RBuilder.newsListView(): ReactElement? = div(classes = "main") {
        headerView()
        console.log(state.news)
        newsListView(state.news!!)
        fabView()
    }

    override fun showList(articles: List<Article>, infos: List<Info>, puzzlers: List<Puzzler>) {
        setState { news = Newses(articles, infos, puzzlers) }
    }
}

external interface NewsComponentState : BaseState {
    var loading: Boolean?
    var news: Newses?
}

data class Newses(val articles: List<Article>, val infos: List<Info>, val puzzlers: List<Puzzler>)