package org.kotlinacademy

import org.kotlinacademy.common.hashRouter
import org.kotlinacademy.common.route
import org.kotlinacademy.common.switch
import org.kotlinacademy.components.FeedbackComponent
import org.kotlinacademy.components.NewsComponent
import org.kotlinacademy.components.TestComponent
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