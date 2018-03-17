package org.kotlinacademy.views

import react.RBuilder
import react.ReactElement
import react.dom.*

fun RBuilder.fabView(): ReactElement? = div(classes = "fab") {
    span(classes="fab-action-button") {
        i(classes = "fab-action-button__icon") {  }
    }
    ul(classes = "fab-buttons") {
        li(classes="fab-buttons__item") {
            a(href = "#", classes = "fab-buttons__link") {
                setProp("data-tooltip", "Add feedback")
                i(classes = "icon-material icon-material_talk") {  }
            }
        }
        li(classes="fab-buttons__item") {
            a(href = "https://blog.kotlin-academy.com/write-for-kotlin-academy-abebd70937ce", classes = "fab-buttons__link") {
                setProp("data-tooltip", "Submit an article")
                i(classes = "icon-material icon-material_plus") {  }
            }
        }
        li(classes="fab-buttons__item") {
            a(href = "#", classes = "fab-buttons__link") {
                setProp("data-tooltip", "Submit news")
                i(classes = "icon-material icon-material_plus") {  }
            }
        }
        li(classes="fab-buttons__item") {
            a(href = "#", classes = "fab-buttons__link") {
                setProp("data-tooltip", "Submit puzzler")
                i(classes = "icon-material icon-material_plus") {  }
            }
        }
    }
}
