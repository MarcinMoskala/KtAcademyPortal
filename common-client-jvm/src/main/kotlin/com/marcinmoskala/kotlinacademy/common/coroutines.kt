package com.marcinmoskala.kotlinacademy.common

import kotlin.coroutines.experimental.CoroutineContext
import kotlin.properties.Delegates.notNull

var UI: CoroutineContext by notNull()

actual fun launchUI(block: suspend () -> Unit) {
    kotlinx.coroutines.experimental.launch(UI) {
        block()
    }
}