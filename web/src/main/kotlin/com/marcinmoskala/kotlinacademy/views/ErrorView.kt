package com.marcinmoskala.kotlinacademy.views

import react.RBuilder
import react.ReactElement
import react.dom.div

fun RBuilder.errorView(error: Throwable): ReactElement? = div(classes = "error") {
    +error.message!!
}