package org.kotlinacademy

import kotlinx.coroutines.experimental.DefaultDispatcher
import org.kotlinacademy.common.hashRouter
import org.kotlinacademy.common.route
import org.kotlinacademy.common.switch
import org.kotlinacademy.components.*
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
                    route("/submit-info", SubmitInfoComponent::class)
                    route("/submit-puzzler", SubmitPuzzlerComponent::class)
                    route("/submit-snippet", SubmitSnippetComponent::class)
                    route("/manager", ManagerComponent::class)
                }
            }
        }
    }
    val registerNotificationTokenService = RegisterNotificationTokenService()
    registerNotificationTokenService.initFirebase()
}