package org.kotlinacademy.views

import kotlinx.html.FORM
import kotlinx.html.id
import org.kotlinacademy.common.*
import react.RBuilder
import react.dom.*

fun RBuilder.kaForm(builder: RDOMBuilder<FORM>.() -> Unit) = div(classes = "list-center") {
    form(classes = "center-text") {
        builder()
    }
}

fun RDOMBuilder<FORM>.textFieldView(text: String, value: String? = null, name: String = randomId(), number: Boolean = false, lines: Int = if (number) 1 else 4, onChange: (String?) -> Unit) {
    div {
        materialTextField {
            +text
            attrs {
                id = name
                this.value = value ?: ""
                label = text
                rows = lines
                multiline = lines > 1
                fullWidth = true
                this.onChange = { e ->
                    onChange(e.target.asDynamic().value.toString().takeUnless { it.isBlank() })
                }
            }
        }
    }
}

fun RDOMBuilder<FORM>.selectFieldView(
        label: String,
        possibilities: List<String>,
        initial: String? = null
): FormFieldText {
    val name = randomId()
    select(classes = "mdc-select__native-control") {
        attrs {
            id = name
        }
        for (p in listOfNotNull(initial) + possibilities) {
            option {
                setProp("value", p)
                +p
            }
        }
    }
    return FormFieldText(name)
}

class FormFieldText(private val id: String) {
    val value: String? get() = valueOn(id)
}