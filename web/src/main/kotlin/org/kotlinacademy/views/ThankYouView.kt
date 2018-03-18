package org.kotlinacademy.views

import react.RBuilder
import react.ReactElement
import react.dom.div
import react.dom.h1
import react.dom.img

fun RBuilder.thankYouView(): ReactElement? = div(classes = "center-on-screen") {
    h1(classes = "center-text") { +"Thank you :)" }
    img(src = "img/thank_you.jpg") {  }
}