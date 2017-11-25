package com.marcinmoskala.kotlinacademy.components

import com.marcinmoskala.kotlinacademy.common.RouteResultProps
import com.marcinmoskala.kotlinacademy.common.async
import com.marcinmoskala.kotlinacademy.common.delay
import com.marcinmoskala.kotlinacademy.presentation.comment.CommentPresenter
import com.marcinmoskala.kotlinacademy.presentation.comment.CommentView
import com.marcinmoskala.kotlinacademy.views.commentFormView
import com.marcinmoskala.kotlinacademy.views.errorView
import com.marcinmoskala.kotlinacademy.views.loadingView
import com.marcinmoskala.kotlinacademy.views.thankYouView
import kotlinx.html.InputType.text
import react.*
import react.dom.*
import kotlin.browser.window
import kotlin.properties.Delegates.observable

// Simple component used to test different views
class TestComponent : BaseComponent<RProps, BaseState>() {

    override fun RBuilder.render(): ReactElement? = thankYouView()
}