package org.kotlinacademy.components

import kotlinx.coroutines.experimental.DefaultDispatcher
import org.kotlinacademy.common.RouteResultProps
import org.kotlinacademy.data.Feedback
import org.kotlinacademy.presentation.feedback.FeedbackPresenter
import org.kotlinacademy.presentation.feedback.FeedbackView
import org.kotlinacademy.respositories.FeedbackRepositoryImpl
import org.kotlinacademy.views.*
import react.RBuilder
import react.RProps
import react.ReactElement
import react.dom.h3
import kotlin.properties.Delegates.observable
import react.setState

class FeedbackComponent : BaseComponent<RouteResultProps<CommentProps>, CommentComponentState>(), FeedbackView {

    private val feedbackRepository = FeedbackRepositoryImpl()
    private val presenter by presenter { FeedbackPresenter(DefaultDispatcher, this, feedbackRepository) }

    override var loading: Boolean by observable(false) { _, _, n ->
        setState { loading = n }
    }

    override fun RBuilder.render() {
        when {
            state.loading == true -> loadingView()
            state.showThankYouPage == true -> thankYouView()
            state.error != null -> errorView(state.error!!)
            else -> kaForm {
                val id = props.match.params.id?.toIntOrNull()
                val general = id == null
                h3 { if (general) +"General comment" else +"Article comment" }

                textFieldView("Please, rate using a number between 0 and 10", number = true, value = state.rating) {
                    setState { rating = it }
                }

                val whatIsCommentAbout = if (general) "Kot. Academy" else "this article"
                textFieldView("What do you think about $whatIsCommentAbout?", value = state.comment){
                    setState { comment = it }
                }

                textFieldView("Can you give us some advice how to make it better?", value = state.suggestions){
                    setState { suggestions = it }
                }

                submitButton("Send", onClick = fun() {
                    val feedback = Feedback(id,
                            rating = state.rating?.toIntOrNull() ?: return,
                            comment = state.comment ?: "",
                            suggestions = state.suggestions ?: ""
                    )
                    presenter.onSendCommentClicked(feedback)
                })
            }
        }
    }

    override fun backToNewsAndShowSuccess() {
        setState { showThankYouPage = true }
        backAfterDelay(millis = 3_000)
    }
}

external interface CommentComponentState : BaseState {
    var rating: String?
    var comment: String?
    var suggestions: String?

    var loading: Boolean?
    var showThankYouPage: Boolean?
}

external interface CommentProps : RProps {
    var id: String?
}