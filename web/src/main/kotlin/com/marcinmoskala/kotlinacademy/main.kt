package com.marcinmoskala.kotlinacademy

import com.marcinmoskala.kotlinacademy.components.MainComponent
import com.marcinmoskala.kotlinacademy.common.hashRouter
import com.marcinmoskala.kotlinacademy.common.route
import com.marcinmoskala.kotlinacademy.common.switch
import react.dom.*
import kotlin.browser.document
import kotlin.browser.window

fun main(args: Array<String>) {
    window.onload = {
        render(document.getElementById("root")!!) {
            child(MainComponent::class) {}
        }
    }
//        render(document.getElementById("root")) {
//            div("App-header") {
//                key = "header"
//                h2 {
//                    +"Welcome to React with Kotlin"
//                }
//            }
//            hashRouter {
//                switch {
//                    route("/", MainComponent::class, exact = true)
//                }
//            }
//        }
}