package org.kotlinacademy.presentation.info

import org.kotlinacademy.data.Info
import org.kotlinacademy.presentation.BaseView

interface InfoView : BaseView {
    var prefilled: Info?
    var loading: Boolean
    fun backToNewsAndShowSuccess()
}