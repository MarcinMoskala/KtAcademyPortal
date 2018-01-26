package org.kotlinacademy.common

import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

// Should be set for different platforms
var UI: CoroutineContext = DefaultDispatcher

fun launchUI(block: suspend () -> Unit): Job = launch(UI) { block() }

expect suspend fun delay(time: Long)