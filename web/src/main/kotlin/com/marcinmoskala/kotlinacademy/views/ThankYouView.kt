package com.marcinmoskala.kotlinacademy.views

import com.marcinmoskala.kotlinacademy.common.HttpError
import kotlinext.js.js
import kotlinx.html.style
import react.RBuilder
import react.ReactElement
import react.dom.*

fun RBuilder.thankYouView(): ReactElement? = div(classes = "center-on-screen") {
    h1(classes = "center-text") { +"Thank you for the comment :)" }
    img(src = "thank_you.jpg") {  }
}