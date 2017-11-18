package com.marcinmoskala.kotlinacademy

import com.marcinmoskala.kotlinacademy.components.MainComponent
import com.marcinmoskala.kotlinacademy.common.hashRouter
import com.marcinmoskala.kotlinacademy.common.route
import com.marcinmoskala.kotlinacademy.common.switch
import react.dom.dialog
import react.dom.render
import kotlin.browser.document
import kotlin.browser.window

fun main(args: Array<String>) {
    window.onload = {
        render(document.getElementById("root")!!) {
            dialog { "AAAA" }
            hashRouter {
                switch {
                    route("/", MainComponent::class, exact = true)
                }
            }
        }
    }
}