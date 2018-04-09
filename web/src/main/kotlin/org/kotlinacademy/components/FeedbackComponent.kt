package org.kotlinacademy.components

import org.kotlinacademy.common.RouteResultProps
import org.kotlinacademy.presentation.feedback.FeedbackPresenter
import org.kotlinacademy.presentation.feedback.FeedbackView
import org.kotlinacademy.views.feedbackFormView
import org.kotlinacademy.views.errorView
import org.kotlinacademy.views.loadingView
import org.kotlinacademy.views.thankYouView
import react.RBuilder
import react.RProps
import react.ReactElement
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
        else -> feedbackFormView(id = props.match.params.id?.toIntOrNull(), onSubmit = presenter::onSendCommentClicked)
    }

    override fun backToNewsAndShowSuccess() {
        setState { showThankYouPage = true }
        backAfterDelay(millis = 3_000)
    }
}

external interface CommentComponentState : BaseState {
    var loading: Boolean?
    var showThankYouPage: Boolean?
}

external interface CommentProps : RProps {
    var id: String?
}
