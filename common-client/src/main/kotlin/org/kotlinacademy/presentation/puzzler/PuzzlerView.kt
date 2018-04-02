package org.kotlinacademy.presentation.puzzler

import org.kotlinacademy.data.Puzzler
import org.kotlinacademy.presentation.BaseView

interface PuzzlerView : BaseView {
    var loading: Boolean
    var prefilled: Puzzler?
    fun backToNewsAndShowSuccess()
}