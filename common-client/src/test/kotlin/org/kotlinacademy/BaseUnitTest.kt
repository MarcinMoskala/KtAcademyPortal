package org.kotlinacademy

import kotlinx.coroutines.experimental.Unconfined
import org.kotlinacademy.common.UI

abstract class BaseUnitTest {
    init {
        UI = Unconfined
    }
}