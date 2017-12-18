package org.kotlinacademy.presentation.feedback

import org.kotlinacademy.presentation.BaseView

interface FeedbackView : BaseView {
    var loading: Boolean
    fun backToNewsAndShowSuccess()
}