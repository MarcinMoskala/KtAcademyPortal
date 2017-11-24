package com.marcinmoskala.kotlinacademy.presentation.comment

import com.marcinmoskala.kotlinacademy.presentation.BaseView

interface CommentView: BaseView {
    var loading: Boolean
    fun backToNewsAndShowSuccess()
}