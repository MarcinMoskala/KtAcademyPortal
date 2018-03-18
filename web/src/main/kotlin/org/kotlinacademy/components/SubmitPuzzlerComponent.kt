package org.kotlinacademy.components

import org.kotlinacademy.presentation.puzzler.PuzzlerPresenter
import org.kotlinacademy.presentation.puzzler.PuzzlerView
import org.kotlinacademy.views.errorView
import org.kotlinacademy.views.loadingView
import org.kotlinacademy.views.puzzlerFormView
import org.kotlinacademy.views.thankYouView
import react.RBuilder
import react.RProps
import react.ReactElement
import kotlin.properties.Delegates.observable

class SubmitPuzzlerComponent : BaseComponent<RProps, SubmitPuzzlerComponentState>(), PuzzlerView {

    private val presenter by presenter { PuzzlerPresenter(this) }

    override var loading: Boolean by observable(false) { _, _, n ->
        setState { state.loading = n }
    }

    override fun RBuilder.render(): ReactElement? = when {
        state.loading == true -> loadingView()
        state.showThankYouPage == true -> thankYouView()
        state.error != null -> errorView(state.error!!)
        else -> puzzlerFormView(onSubmit = presenter::onSubmitCommentClicked)
    }

    override fun backToNewsAndShowSuccess() {
        setState { showThankYouPage = true }
        backToRootAfterDelay(millis = 3_000)
    }
}

external interface SubmitPuzzlerComponentState : BaseState {
    var loading: Boolean?
    var showThankYouPage: Boolean?
}