package org.kotlinacademy.presentation.feedback

import org.kotlinacademy.common.launchUI
import org.kotlinacademy.data.Feedback
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.FeedbackRepository

class FeedbackPresenter(
        private val view: FeedbackView,
        private val feedbackRepository: FeedbackRepository
) : BasePresenter() {

    fun onSendCommentClicked(feedback: Feedback) {
        view.loading = true
        jobs += launchUI {
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