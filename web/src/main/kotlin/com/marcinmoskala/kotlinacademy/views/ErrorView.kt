package com.marcinmoskala.kotlinacademy.views

import com.marcinmoskala.kotlinacademy.common.HttpError
import react.RBuilder
import react.ReactElement
import react.dom.div
import react.dom.h3

fun RBuilder.errorView(error: Throwable): ReactElement? = div(classes = "error") {
    val message = if (error is HttpError) "Http ${error.code} error :(<br>Message: ${error.message}" else error.message.orEmpty()
    h3(classes = "center-on-screen") { +message }
}