package org.kotlinacademy.common

import org.kotlinacademy.views.hide
import org.w3c.dom.Element
import org.w3c.dom.ItemArrayLike
import org.w3c.dom.get
import kotlin.browser.document

fun applyCodeHighlighting() {
    js("KotlinPlayground('.kotlin');")

    // Dirty hack, TODO: Should happend after playground is loaded
    setTimeout({
        document.getElementsByClassName("initially-hidden-start-button")
                .forEach { elem ->
                    elem?.getStartButtonForCodeBlock()?.also { println(it) }?.hide()
                }
    }, 1000L)
}

fun Element.getStartButtonForCodeBlock() = parentElement?.getElementsByClassName("run-button")?.get(0)

fun <T> ItemArrayLike<T>.forEach(f: (T?) -> Unit) {
    for (i in 0 until length) {
        f(item(i))
    }
}