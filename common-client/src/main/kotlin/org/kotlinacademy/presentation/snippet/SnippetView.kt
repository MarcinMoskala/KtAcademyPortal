package org.kotlinacademy.presentation.snippet

import org.kotlinacademy.data.Puzzler
import org.kotlinacademy.data.Snippet
import org.kotlinacademy.presentation.BaseView

interface SnippetView : BaseView {
    var loading: Boolean
    var prefilled: Snippet?
    fun backToNewsAndShowSuccess()
}