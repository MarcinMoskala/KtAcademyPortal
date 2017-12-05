package org.kotlinacademy.views

import org.kotlinacademy.data.Feedback
import kotlinx.html.FORM
import kotlinx.html.InputType
import kotlinx.html.InputType.number
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import react.RBuilder
import react.ReactElement
import react.dom.*

fun RBuilder.commentFormView(id: Int?, onSubmit: (Feedback) -> Unit): ReactElement? = div(classes = "list-center") {
    form(classes = "center-text") {
        val general = id == null
        h3 { if (general) +"General comment" else +"Article comment" }

        var rating: Int? = null
        var commentText: String? = null
        var suggestionsText: String? = null

        numberFieldView(
                text = "Please, rate using a number between 0 and 10",
                onNumberChanged = { rating = it }
        )

        textFieldView(
                text = "What do you think about ${if (general) "Kotlin Academy" else "this news"}?",
                onTextChanged = { commentText = it }
        )

        textFieldView(
                text = "Can you give us some advice how to make it better?",
                onTextChanged = { suggestionsText = it }
        )

        button(classes = "mdc-button mdc-button--raised") {
            attrs {
                onClickFunction = fun(_) {
                    val feedback = Feedback(
                            newsId = id,
                            rating = rating ?: return,
                            comment = commentText ?: "",
                            suggestions = suggestionsText ?: ""
                    )
                    onSubmit(feedback)
                }
            }
            +"Send"
        }
    }
}

private fun RDOMBuilder<FORM>.numberFieldView(text: String, onNumberChanged: (Int?)->Unit) {
    div(classes = "mdc-text-field mdc-text-field--textarea") {
        input (classes = "mdc-text-field__input", type = number) {
            setProp("placeholder", text)

            attrs {
                onChangeFunction = {
                    val target = it.target as HTMLInputElement
                    onNumberChanged(target.value.toIntOrNull())
                }
            }
        }
    }
}

private fun RDOMBuilder<FORM>.textFieldView(text: String, onTextChanged: (String)->Unit) {
    div(classes = "mdc-text-field mdc-text-field--textarea") {
        textArea(classes = "mdc-text-field__input input-text-comment") {
            setProp("type", "text")
            setProp("cols", "40")
            setProp("rows", "8")
            setProp("placeholder", text)

            attrs {
                onChangeFunction = {
                    val target = it.target as HTMLTextAreaElement
                    onTextChanged(target.value)
                }
            }
        }
    }
}