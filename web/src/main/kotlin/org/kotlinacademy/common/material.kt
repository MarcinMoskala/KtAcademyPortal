@file:JsModule("@material-ui/core")

package org.kotlinacademy.common

import org.w3c.dom.events.Event
import react.*

@JsName("Button")
external class MaterialButton : Component<MaterialButtonProps, RState> {
    override fun render(): ReactElement?
}

external interface MaterialButtonProps : RProps {
    var id: String
    var variant: String
    var color: String
    var style: ButtonStyle
    var onClick: () -> Unit
}

@JsName("TextField")
external class MaterialTextField : Component<MaterialTextFieldProps, RState> {
    override fun render(): ReactElement?
}

external interface MaterialTextFieldProps : RProps {
    var onChange: (e: Event) -> Unit
    var id: String
    var value: String
    var label: String
    var rows: Int
    var multiline: Boolean
    var fullWidth: Boolean
    var margin: String
}


@JsName("FormControl")
external class MaterialFormControl : Component<MaterialFormControlProps, RState> {
    override fun render(): ReactElement?
}

external interface MaterialFormControlProps : RProps {
}

@JsName("InputLabel")
external class MaterialInputLabel : Component<MaterialInputLabelProps, RState> {
    override fun render(): ReactElement?
}

external interface MaterialInputLabelProps : RProps {
}

@JsName("Select")
external class MaterialSelect : Component<MaterialSelectProps, RState> {
    override fun render(): ReactElement?
}

external interface MaterialSelectProps : RProps {
    var value: String
    var onChange: ()->Unit
}

@JsName("MenuItem")
external class MaterialMenuItem : Component<MaterialMenuItemProps, RState> {
    override fun render(): ReactElement?
}

external interface MaterialMenuItemProps : RProps {
    var value: String
}