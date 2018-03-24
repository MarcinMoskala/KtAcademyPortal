package org.kotlinacademy.components

import org.kotlinacademy.common.getUrlParam
import org.kotlinacademy.data.News
import org.kotlinacademy.data.NewsData
import org.kotlinacademy.presentation.news.NewsPresenter
import org.kotlinacademy.presentation.news.NewsView
import org.kotlinacademy.views.*
import react.RBuilder
import react.RProps
import react.ReactElement
import react.dom.div
import kotlin.browser.document
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

    override fun componentDidUpdate(prevProps: RProps, prevState: NewsComponentState) {
        jumpToTag()
    }

    private fun RBuilder.newsListView(): ReactElement? = div(classes = "main") {
        headerView()
        newsListView(state.news!!)
        fabView()
    }

    override fun showList(news: List<News>, newsData: NewsData) {
        setState { this.news = newsData }
    }

    private fun jumpToTag() {
        if (state.loading == false && state.news != null) {
            val tag = getUrlParam("tag")
            if (tag != null && tag.isNotBlank()) {
                val node = document.getElementById(tag)
                node?.scrollIntoView()
            }
        }
    }
}

external interface NewsComponentState : BaseState {
    var loading: Boolean?
    var news: NewsData?
}