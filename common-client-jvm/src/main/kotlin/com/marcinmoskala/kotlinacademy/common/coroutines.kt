package com.marcinmoskala.kotlinacademy.common

import kotlinx.coroutines.experimental.android.UI

actual fun launchUI(block: suspend () -> Unit) {
    kotlinx.coroutines.experimental.launch(UI) {
        block()
    }
}