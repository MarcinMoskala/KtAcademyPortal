package com.marcinmoskala.kotlinacademy.presentation.comment

import com.marcinmoskala.kotlinacademy.common.launchUI
import com.marcinmoskala.kotlinacademy.data.Comment
import com.marcinmoskala.kotlinacademy.presentation.BasePresenter
import com.marcinmoskala.kotlinacademy.respositories.CommentRepository

class CommentPresenter(val view: CommentView): BasePresenter() {

    private val commentRepository by CommentRepository.lazyGet()

    fun onSendComment(comment: Comment) {
        view.loading = true
        jobs += launchUI {
            try {
                commentRepository.addComment(comment)
                view.backToNewsList()
            } catch (e: Throwable) {
                view.showError(e)
            } finally {
                view.loading = false
            }
        }
    }
}