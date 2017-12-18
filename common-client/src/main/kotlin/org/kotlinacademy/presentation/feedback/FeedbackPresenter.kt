package org.kotlinacademy.presentation.feedback

import org.kotlinacademy.common.launchUI
import org.kotlinacademy.data.Feedback
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.FeedbackRepository

class FeedbackPresenter(val view: FeedbackView): BasePresenter() {

    private val commentRepository by FeedbackRepository.lazyGet()

    fun onSendCommentClicked(feedback: Feedback) {
        view.loading = true
        jobs += launchUI {
            try {
                commentRepository.addComment(feedback)
                view.backToNewsAndShowSuccess()
            } catch (e: Throwable) {
                view.showError(e)
            } finally {
                view.loading = false
            }
        }
    }
}