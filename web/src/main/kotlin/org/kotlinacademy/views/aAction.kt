package org.kotlinacademy.views

import kotlinx.html.A
import kotlinx.html.js.onClickFunction
import org.kotlinacademy.common.sendEvent
import react.dom.RDOMBuilder
import react.dom.a

fun RDOMBuilder<*>.aAction(
        href: String?,
        classes: String = "",
        category: String,
        action: String = "open",
        extra: String = "",
        newCard: Boolean = true,
        f: RDOMBuilder<A>.() -> Unit
) {
    a(href = href, classes = classes, target = if (newCard) "_blank" else "") {
        f()
        if (href != null) {
            attrs {
                onClickFunction = {
                    sendEvent(category, action, extra)
                }
            }
        }
    }
}
