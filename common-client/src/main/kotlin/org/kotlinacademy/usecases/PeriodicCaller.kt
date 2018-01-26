package org.kotlinacademy.usecases

import kotlinx.coroutines.experimental.Job
import org.kotlinacademy.common.Provider
import org.kotlinacademy.common.delay
import org.kotlinacademy.common.launchUI

interface PeriodicCaller {

    fun start(timeMillis: Long, callback: () -> Unit): Job

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