package org.kotlinacademy.common

import org.w3c.dom.asList
import kotlin.browser.document

private external val hljs: dynamic

fun applyCodeHighlighting() {
    document.getElementsByClassName("kotlin").asList().forEach {
        hljs.highlightBlock(it)
    }
}