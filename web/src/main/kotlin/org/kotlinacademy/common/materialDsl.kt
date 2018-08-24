package org.kotlinacademy.common

import kotlinx.html.CommonAttributeGroupFacade
import org.w3c.dom.events.Event
import react.RBuilder
import react.RHandler
import react.RProps

fun RBuilder.materialButton(handler: RHandler<MaterialButtonProps>) = child(MaterialButton::class) {
    attrs {
        this.variant = "raised"
        this.style = ButtonStyle("#f47421", "#FFFFFF")
    }
    handler()
}

class ButtonStyle(val backgroundColor: String, val color: String)

fun RBuilder.materialTextField(handler: RHandler<MaterialTextFieldProps>) = child(MaterialTextField::class) {
    attrs { margin = "normal" }
    handler()
}

fun RBuilder.materialFormControl(handler: RHandler<MaterialFormControlProps>) = child(MaterialFormControl::class, handler)

fun RBuilder.materialSelect(handler: RHandler<MaterialSelectProps>) = child(MaterialSelect::class, handler)

fun RBuilder.materialMenuItem(handler: RHandler<MaterialMenuItemProps>) = child(MaterialMenuItem::class, handler)

fun RBuilder.materialInputLabel(handler: RHandler<MaterialInputLabelProps>) = child(MaterialInputLabel::class, handler)
