package org.kotlinacademy.views

import org.kotlinacademy.common.materialButton
import react.dom.RDOMBuilder

fun RDOMBuilder<*>.submitButton(text: String, onClick: () -> Unit) {
    materialButton {
        +text
        attrs { this.onClick = onClick }
    }
}
