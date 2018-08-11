package org.kotlinacademy.common

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.kotlinacademy.views.hide
import org.w3c.dom.Element
import org.w3c.dom.ItemArrayLike
import org.w3c.dom.get
import kotlin.browser.document

fun applyCodeHighlighting() {
    js("KotlinPlayground('.kotlin');")

    // Dirty hack, TODO: Should happend after playground is loaded
    launch {
        delay(1000L)
        document.getElementsByClassName("initially-hidden-start-button")
                .forEach { elem ->
                    elem?.getStartButtonForCodeBlock()?.also { println(it) }?.hide()
                }
    }
}

fun Element.getStartButtonForCodeBlock() = parentElement?.getElementsByClassName("run-button")?.get(0)

fun <T> ItemArrayLike<T>.forEach(f: (T?) -> Unit) {
    for (i in 0 until length) {
        f(item(i))
    }
}