package org.kotlinacademy.presentation.info

import org.kotlinacademy.presentation.BaseView

interface InfoView : BaseView {
    var loading: Boolean
    fun backToNewsAndShowSuccess()
}