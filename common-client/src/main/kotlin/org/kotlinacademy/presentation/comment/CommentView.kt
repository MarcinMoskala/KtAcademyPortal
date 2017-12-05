package org.kotlinacademy.presentation.comment

import org.kotlinacademy.presentation.BaseView

interface CommentView: BaseView {
    var loading: Boolean
    fun backToNewsAndShowSuccess()
}