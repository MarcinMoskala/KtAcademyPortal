package org.kotlinacademy.views

import kotlinx.html.js.onClickFunction
import react.dom.RDOMBuilder
import react.dom.div

fun RDOMBuilder<*>.submitButton(text: String, onClick: () -> Unit) {
    div(classes = "mdc-button mdc-button--raised") {
        attrs {
            onClickFunction = { onClick() }
        }
        +text
    }
}