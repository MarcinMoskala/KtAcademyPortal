package com.marcinmoskala.kotlinacademy.views

import react.RBuilder
import react.ReactElement
import react.dom.div
import react.dom.img

fun RBuilder.loadingView(): ReactElement? = div(classes = "loading") {
    img(classes = "center-on-screen spinner", src = "spinner.gif") {}
}