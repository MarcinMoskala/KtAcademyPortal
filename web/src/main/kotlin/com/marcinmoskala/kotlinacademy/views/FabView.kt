package com.marcinmoskala.kotlinacademy.views

import kotlinx.html.attributesMapOf
import react.RBuilder
import react.ReactElement
import react.dom.a
import react.dom.img
import react.dom.nav

fun RBuilder.fabView(): ReactElement? = nav(classes = "fab") {
// Left to add social share
//    a(href = "#", classes = "fab-buttons") { }
    a(href = "#/comment/null", classes = "fab-buttons") {
        img(classes = "fab-dialog-icon-center", src = "talk_icon.png") {  }
    }
}
