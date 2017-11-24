package com.marcinmoskala.kotlinacademy.views

import com.marcinmoskala.kotlinacademy.data.Comment
import kotlinx.html.FORM
import kotlinx.html.InputType.text
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import react.RBuilder
import react.ReactElement
import react.dom.*

fun RBuilder.commentFormView(id: Int?, onSubmit: (Comment) -> Unit): ReactElement? = div(classes = "list-center") {
    form {
        val general = id == null
        h3 { if (general) +"General comment" else +"Article comment" }

        var rating: Int? = null
        var commentText: String? = null
        var suggestionsText: String? = null

        div(classes = "mdl-textfield mdl-js-textfield form-full-width") {
            input(classes = "mdl-textfield__input form-full-width", type = text) {
                setProp("pattern", "[1-9]|10")
                attrs {
                    onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        rating = target.value.toIntOrNull()
                    }
                }
            }
            label(classes = "mdl-textfield__label") { +"Can you rate it?" }
            span(classes = "mdl-textfield__error") { +"Please, rate using a number between 0 and 10" }
        }

        textFieldView(
                text = "What do you think about ${if (general) "Kotlin Academy" else "this news"}?",
                onTextChanged = { commentText = it }
        )

        textFieldView(
                text = "Can you give us some advice how to make it better?",
                onTextChanged = { suggestionsText = it }
        )

        button(classes = "mdl-button mdl-js-button mdl-button--raised mdl-button--colored") {
            attrs {
                onClickFunction = fun(_) {
                    val comment = Comment(
                            newsId = id,
                            rating = rating ?: return,
                            comment = commentText ?: "",
                            suggestions = suggestionsText ?: ""
                    )
                    println(comment)
                    onSubmit(comment)
                }
            }
            +"Send"
        }
    }
}

private fun RDOMBuilder<FORM>.textFieldView(text: String, onTextChanged: (String)->Unit) {
    div(classes = "mdl-textfield mdl-js-textfield form-full-width") {
        textArea(classes = "mdl-textfield__input form-full-width") {
            setProp("id", "name")
            setProp("type", "text")
            setProp("rows", "3")
            attrs {
                onChangeFunction = {
                    val target = it.target as HTMLTextAreaElement
                    onTextChanged(target.value)
                }
            }
        }
        label(classes = "mdl-textfield__label") {
            +text
        }
    }
}