package org.kotlinacademy.components

import kotlinx.coroutines.experimental.launch
import org.kotlinacademy.common.delay
import org.kotlinacademy.presentation.feedback.FeedbackPresenter
import org.kotlinacademy.presentation.feedback.FeedbackView
import org.kotlinacademy.views.*
import react.RBuilder
import react.RProps
import react.ReactElement
import react.dom.div
import kotlin.browser.window
import kotlin.properties.Delegates.observable

class SubmitPuzzlerComponent : BaseComponent<RProps, SubmitPuzzlerComponentState>(), FeedbackView {

    private val presenter by presenter { FeedbackPresenter(this) }

    override var loading: Boolean by observable(false) { _, _, n ->
        setState { state.loading = n }
    }

    override fun RBuilder.render(): ReactElement? = when {
        state.loading == true -> loadingView()
        state.showThankYouPage == true -> thankYouView()
        state.error != null -> errorView(state.error!!)
        else -> puzzlerFormView(onSubmit = {})
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