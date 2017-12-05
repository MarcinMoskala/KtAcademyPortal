package org.kotlinacademy.presentation.comment

import org.kotlinacademy.common.launchUI
import org.kotlinacademy.data.Feedback
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.CommentRepository

class CommentPresenter(val view: CommentView): BasePresenter() {

    private val commentRepository by CommentRepository.lazyGet()

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