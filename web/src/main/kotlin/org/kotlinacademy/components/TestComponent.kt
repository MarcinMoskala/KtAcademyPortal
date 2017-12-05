package org.kotlinacademy.components

import org.kotlinacademy.views.thankYouView
import react.RBuilder
import react.RProps
import react.ReactElement

// Simple component used to test different views
class TestComponent : BaseComponent<RProps, BaseState>() {

    override fun RBuilder.render(): ReactElement? = thankYouView()
}