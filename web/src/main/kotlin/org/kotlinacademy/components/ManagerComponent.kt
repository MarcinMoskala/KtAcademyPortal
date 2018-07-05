package org.kotlinacademy.components

import org.kotlinacademy.common.secretInUrl
import org.kotlinacademy.data.Info
import org.kotlinacademy.data.News
import org.kotlinacademy.data.Puzzler
import org.kotlinacademy.presentation.manager.ManagerPresenter
import org.kotlinacademy.presentation.manager.ManagerView
import org.kotlinacademy.respositories.ManagerRepositoryImpl
import org.kotlinacademy.views.*
import react.RBuilder
import react.RProps
import react.ReactElement
import react.dom.div
import kotlin.properties.Delegates.observable

class ManagerComponent : BaseComponent<RProps, ManagerComponentState>(), ManagerView {

    private val managerRepository = ManagerRepositoryImpl()
    private val secret by lazy { secretInUrl ?: "" }
    private val presenter by presenter { ManagerPresenter(this, secret, managerRepository) }

    override var loading: Boolean by observable(false) { _, _, n ->
        setState { state.loading = n }
    }

    override fun RBuilder.render(): ReactElement? = when {
        state.loading == true -> loadingView()
        state.error != null -> errorView(state.error!!)
        else -> propositionListView()
    }

    override fun showList(news: List<News>) {
        setState { this.propositions = news }
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