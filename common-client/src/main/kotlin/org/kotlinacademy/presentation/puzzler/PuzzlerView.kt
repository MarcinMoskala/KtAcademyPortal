package org.kotlinacademy.presentation.puzzler

import org.kotlinacademy.presentation.BaseView

interface PuzzlerView : BaseView {
    var loading: Boolean
    fun backToNewsAndShowSuccess()
}