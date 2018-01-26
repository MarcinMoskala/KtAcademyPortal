package org.kotlinacademy.components

import kotlinx.coroutines.experimental.launch
import org.kotlinacademy.common.RouteResultProps
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
import kotlin.browser.window
import kotlin.properties.Delegates.observable

class FeedbackComponent : BaseComponent<RouteResultProps<CommentProps>, CommentComponentState>(), FeedbackView {

    private val presenter by presenter { FeedbackPresenter(this) }

    override var loading: Boolean by observable(false) { _, _, n ->
        setState { state.loading = n }
    }

    override fun RBuilder.render(): ReactElement? = when {
        state.loading == true -> loadingView()
        state.showThankYouPage == true -> thankYouView()
        state.error != null -> errorView(state.error!!)
        else -> commentFormView(id = props.match.params.id?.toIntOrNull(), onSubmit = presenter::onSendCommentClicked)
    }

    override fun backToNewsAndShowSuccess() {
        launch {
            setState { showThankYouPage = true }
            delay(3_000)
            window.location.replace("/")
        }
    }

    override fun componentDidMount() {

    }
}

external interface CommentComponentState : BaseState {
    var loading: Boolean?
    var showThankYouPage: Boolean?
}

external interface CommentProps : RProps {
    var id: String?
}
