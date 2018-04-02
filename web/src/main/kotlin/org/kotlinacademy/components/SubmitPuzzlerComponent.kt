package org.kotlinacademy.components

import org.kotlinacademy.common.getUrlParam
import org.kotlinacademy.common.secretInUrl
import org.kotlinacademy.data.Puzzler
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

    private val presenter by presenter { PuzzlerPresenter(this, id = getUrlParam("id")?.toIntOrNull(), secret = secretInUrl) }

    override var loading: Boolean by observable(false) { _, _, n ->
        setState { state.loading = n }
    }
    override var prefilled: Puzzler? by observable(null as Puzzler?) { _, _, n ->
        setState { state.prefilled = n }
    }

    override fun RBuilder.render(): ReactElement? = when {
        state.loading == true -> loadingView()
        state.showThankYouPage == true -> thankYouView()
        state.error != null -> errorView(state.error!!)
        else -> puzzlerFormView(initial = prefilled?.data, onSubmit = presenter::onSubmitClicked)
    }

    override fun backToNewsAndShowSuccess() {
        setState { showThankYouPage = true }
        backAfterDelay(millis = 3_000)
    }
}

external interface SubmitPuzzlerComponentState : BaseState {
    var prefilled: Puzzler?
    var loading: Boolean?
    var showThankYouPage: Boolean?
}