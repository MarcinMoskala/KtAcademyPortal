package org.kotlinacademy.common

import org.w3c.dom.asList
import kotlin.browser.document

fun applyCodeHighlighting() {
    js("KotlinPlayground('.kotlin');")
}