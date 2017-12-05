package org.kotlinacademy.components

import org.kotlinacademy.common.RouteResultProps
import org.kotlinacademy.common.async
import org.kotlinacademy.common.delay
import org.kotlinacademy.presentation.comment.CommentPresenter
import org.kotlinacademy.presentation.comment.CommentView
import org.kotlinacademy.views.commentFormView
import org.kotlinacademy.views.errorView
import org.kotlinacademy.views.loadingView
import org.kotlinacademy.views.thankYouView
import kotlinx.html.InputType.text
import react.*
import react.dom.*
import kotlin.browser.window
import kotlin.properties.Delegates.observable

class FeedbackComponent : BaseComponent<RouteResultProps<CommentProps>, CommentComponentState>(), CommentView {

    private val presenter by presenter { CommentPresenter(this) }

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
        async {
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
