package org.kotlinacademy.components

import kotlinx.coroutines.experimental.DefaultDispatcher
import org.kotlinacademy.common.applyCodeHighlighting
import org.kotlinacademy.common.getUrlParam
import org.kotlinacademy.data.News
import org.kotlinacademy.presentation.news.NewsPresenter
import org.kotlinacademy.presentation.news.NewsView
import org.kotlinacademy.respositories.NewsRepositoryImpl
import org.kotlinacademy.views.*
import react.RBuilder
import react.RProps
import react.ReactElement
import react.dom.div
import react.setState
import kotlin.browser.document
import kotlin.properties.Delegates.observable

class NewsComponent : BaseComponent<RProps, NewsComponentState>(), NewsView {

    private val newsRepository = NewsRepositoryImpl()

    private val presenter by presenter { NewsPresenter(DefaultDispatcher, this, newsRepository) }

    override var loading: Boolean by observable(false) { _, _, n ->
        setState { loading = n }
    }
    override var refresh: Boolean by observable(false) { _, _, n ->
        setState { loading = n }
    }

    override fun RBuilder.render() {
        when {
            loading -> loadingView()
            state.error != null -> errorView(state.error!!)
            else -> newsListView()
        }
    }

    override fun componentDidUpdate(prevProps: RProps, prevState: NewsComponentState, snapshot: Any) {
        jumpToTag()
        applyCodeHighlighting()
    }

    private fun RBuilder.newsListView(): ReactElement? = div(classes = "main") {
        headerView()
        newsListView(state.news.orEmpty())
        fabView()
    }

    override fun showList(news: List<News>) {
        setState { this.news = news }
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
    var news: List<News>?
}