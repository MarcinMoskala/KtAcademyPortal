package com.marcinmoskala.kotlinacademy.views

import com.marcinmoskala.kotlinacademy.common.HttpError
import kotlinext.js.js
import kotlinx.html.style
import react.RBuilder
import react.ReactElement
import react.dom.div
import react.dom.h3
import react.dom.img
import react.dom.style

fun RBuilder.thankYouView(): ReactElement? = div(classes = "center-on-screen") {
    h3() { +"Thank you for the comment :)" }
    img(src = "thank_you.jpg") {  }
}