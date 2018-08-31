package org.kotlinacademy.components

import kotlinx.coroutines.experimental.DefaultDispatcher
import org.kotlinacademy.common.secretInUrl
import org.kotlinacademy.data.Info
import org.kotlinacademy.data.News
import org.kotlinacademy.data.Puzzler
import org.kotlinacademy.data.Snippet
import org.kotlinacademy.presentation.manager.ManagerPresenter
import org.kotlinacademy.presentation.manager.ManagerView
import org.kotlinacademy.respositories.ManagerRepositoryImpl
import org.kotlinacademy.views.*
import react.RBuilder
import react.RProps
import react.ReactElement
import react.dom.div
import kotlin.properties.Delegates.observable
import react.setState

class ManagerComponent : BaseComponent<RProps, ManagerComponentState>(), ManagerView {

    private val managerRepository = ManagerRepositoryImpl()
    private val secret by lazy { secretInUrl ?: "" }
    private val presenter by presenter { ManagerPresenter(DefaultDispatcher, this, secret, managerRepository) }

    override var loading: Boolean by observable(false) { _, _, n ->
        setState { loading = n }
    }

    override fun RBuilder.render() {
        when {
            state.loading == true -> loadingView()
            state.error != null -> errorView(state.error!!)
            else -> propositionListView()
        }
    }

    override fun showList(news: List<News>) {
        setState { propositions = news }
    }

    private fun RBuilder.propositionListView(): ReactElement? = div(classes = "main") {
        headerView()
        div(classes = "list-center") {
            for (n in state.propositions.orEmpty()) {
                when (n) {
                    is Info -> {
                        infoCard(n)
                        submitButton("Accept", onClick = { presenter.acceptInfo(n.id) })
                        submitButton("Reject", onClick = { presenter.rejectInfo(n.id) })
                    }
                    is Puzzler -> {
                        puzzlerCard(n)
                        submitButton("Accept", onClick = { presenter.acceptPuzzler(n.id) })
                        submitButton("Accept important", onClick = { presenter.acceptImportantPuzzler(n.id) })
                        submitButton("To top", onClick = { presenter.puzzlerToTop(n.id) })
                        submitButton("Reject", onClick = { presenter.rejectPuzzler(n.id) })
                    }
                    is Snippet -> {
                        snippetCard(n)
                        submitButton("Accept", onClick = { presenter.acceptSnippet(n.id) })
                        submitButton("Reject", onClick = { presenter.rejectSnippet(n.id) })
                    }
                }
            }
        }
        fabView()
    }
}

external interface ManagerComponentState : BaseState {
    var propositions: List<News>?
    var loading: Boolean?
}