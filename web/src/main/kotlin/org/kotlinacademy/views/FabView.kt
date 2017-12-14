package org.kotlinacademy.views

import react.RBuilder
import react.ReactElement
import react.dom.a
import react.dom.img
import react.dom.nav

fun RBuilder.fabView(): ReactElement? = nav(classes = "fab") {
    a(href = "#/feedback/null", classes = "fab-buttons") {
        img(classes = "fab-dialog-icon-center", src = "img/talk_icon.png") {  }
    }
}
