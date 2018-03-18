package org.kotlinacademy.components

import kotlinx.coroutines.experimental.launch
import org.kotlinacademy.common.delay
import org.kotlinacademy.presentation.feedback.FeedbackPresenter
import org.kotlinacademy.presentation.feedback.FeedbackView
import org.kotlinacademy.views.commentFormView
import org.kotlinacademy.views.errorView
import org.kotlinacademy.views.loadingView
import org.kotlinacademy.views.thankYouView
import react.RBuilder
import react.RProps
import react.ReactElement
import react.dom.div
import kotlin.browser.window
import kotlin.properties.Delegates.observable

class SubmitInfoComponent : BaseComponent<RProps, SubmitInfoComponentState>(), FeedbackView {

    private val presenter by presenter { FeedbackPresenter(this) }

    override var loading: Boolean by observable(false) { _, _, n ->
        setState { state.loading = n }
    }

    override fun RBuilder.render(): ReactElement? = when {
        state.loading == true -> loadingView()
        state.showThankYouPage == true -> thankYouView()
        state.error != null -> errorView(state.error!!)
        else -> div {  }
    }

    override fun backToNewsAndShowSuccess() {
        setState { showThankYouPage = true }
        backToRootAfterDelay(millis = 3_000)
    }
}

external interface SubmitInfoComponentState : BaseState {
    var loading: Boolean?
    var showThankYouPage: Boolean?
}