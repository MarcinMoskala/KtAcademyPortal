package org.kotlinacademy.views

import kotlinx.html.js.onClickFunction
import react.dom.RDOMBuilder
import react.dom.button

fun RDOMBuilder<*>.submitButton(text: String, onClick: () -> Unit) {
    button(classes = "mdc-button mdc-button--raised") {
        attrs {
            onClickFunction = { onClick() }
        }
        +text
    }
}