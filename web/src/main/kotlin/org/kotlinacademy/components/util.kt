package org.kotlinacademy.components

import kotlinext.js.clone
import kotlinext.js.jsObject
import react.RState
import react.React

inline fun <T : RState> React.Component<*, T>.setState(action: T.() -> Unit) {
    setState(jsObject(action))
}

@Suppress("NOTHING_TO_INLINE")
inline fun Double.toFixed(precision: Int): String = asDynamic().toFixed(precision) as String