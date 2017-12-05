package org.kotlinacademy.usecases

import org.kotlinacademy.common.Cancellable
import org.kotlinacademy.common.Provider
import org.kotlinacademy.common.delay
import org.kotlinacademy.common.launchUI

interface PeriodicCaller {

    fun start(timeMillis: Long, callback: () -> Unit): Cancellable

    class PeriodicCallerImpl : PeriodicCaller {
        override fun start(timeMillis: Long, callback: () -> Unit) = launchUI {
            while (true) {
                delay(timeMillis)
                callback()
            }
        }
    }

    companion object : Provider<PeriodicCaller>() {
        override fun create(): PeriodicCaller = PeriodicCallerImpl()
    }
}