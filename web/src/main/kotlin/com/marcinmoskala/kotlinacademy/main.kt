package com.marcinmoskala.kotlinacademy

import com.marcinmoskala.kotlinacademy.common.hashRouter
import com.marcinmoskala.kotlinacademy.common.route
import com.marcinmoskala.kotlinacademy.common.switch
import com.marcinmoskala.kotlinacademy.components.FeedbackComponent
import com.marcinmoskala.kotlinacademy.components.NewsComponent
import com.marcinmoskala.kotlinacademy.components.TestComponent
import react.dom.render
import kotlin.browser.document
import kotlin.browser.window

fun main(args: Array<String>) {
    window.onload = {
        render(document.getElementById("root")!!) {
            hashRouter {
                switch {
                    route("/", NewsComponent::class, exact = true)
                    route("/feedback/:id", FeedbackComponent::class)
                    route("/test", TestComponent::class)
                }
            }
        }
    }
}