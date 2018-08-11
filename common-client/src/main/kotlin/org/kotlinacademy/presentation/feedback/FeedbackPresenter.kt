package org.kotlinacademy.presentation.feedback

import kotlinx.coroutines.experimental.launch
import org.kotlinacademy.data.Feedback
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.FeedbackRepository
import kotlin.coroutines.experimental.CoroutineContext

class FeedbackPresenter(
        private val uiContext: CoroutineContext,
        private val view: FeedbackView,
        private val feedbackRepository: FeedbackRepository
) : BasePresenter() {

    fun onSendCommentClicked(feedback: Feedback) {
        view.loading = true
        jobs += launch(uiContext) {
            try {
                feedbackRepository.addFeedback(feedback)
                view.backToNewsAndShowSuccess()
            } catch (e: Throwable) {
                view.showError(e)
            } finally {
                view.loading = false
            }
        }
    }
}