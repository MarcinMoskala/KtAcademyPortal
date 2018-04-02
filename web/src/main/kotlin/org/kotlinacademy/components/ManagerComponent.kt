package org.kotlinacademy.components

import org.kotlinacademy.common.getUrlParam
import org.kotlinacademy.data.News
import org.kotlinacademy.presentation.manager.ManagerPresenter
import org.kotlinacademy.presentation.manager.ManagerView
import org.kotlinacademy.views.*
import react.RBuilder
import react.RProps
import react.ReactElement
import react.dom.div
import kotlin.properties.Delegates.observable

class ManagerComponent : BaseComponent<RProps, ManagerComponentState>(), ManagerView {

    val secret by lazy { getUrlParam("secret") ?: "" }
    private val presenter by presenter { ManagerPresenter(this, secret) }

    override var loading: Boolean by observable(false) { _, _, n ->
        setState { state.loading = n }
    }

    override fun RBuilder.render(): ReactElement? = when {
        state.loading == true -> loadingView()
        state.error != null -> errorView(state.error!!)
        else -> propositionListView()
    }

    override fun showList(news: List<News>) {
        console.log(news)
        setState { this.propositions = news }
    }

    private fun RBuilder.propositionListView(): ReactElement? = div(classes = "main") {
        headerView()
        newsListView(state.propositions.orEmpty())
        fabView()
    }
}

external interface ManagerComponentState : BaseState {
    var propositions: List<News>?
    var loading: Boolean?
}