package com.marcinmoskala.kotlinacademy

import com.marcinmoskala.kotlinacademy.components.NewsComponent
import react.dom.render
import kotlin.browser.document
import kotlin.browser.window

fun main(args: Array<String>) {
    window.onload = {
        render(document.getElementById("root")!!) {
            child(NewsComponent::class) {}
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
//                    route("/", NewsComponent::class, exact = true)
//                }
//            }
//        }
}