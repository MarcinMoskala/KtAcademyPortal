package org.kotlinacademy.views

import react.RBuilder
import react.ReactElement
import react.dom.div
import react.dom.h1
import react.dom.h2

fun RBuilder.headerView(): ReactElement? = div(classes = "header") {
    div(classes = "header-content") {
        div(classes = "list-center") {
            h1(classes = "header-title default-font") {
                +"Kotlin Academy"
            }
            h2(classes = "header-subtitle default-font") {
                +"With mission to simplify Kotlin learning"
            }
        }
    }
}