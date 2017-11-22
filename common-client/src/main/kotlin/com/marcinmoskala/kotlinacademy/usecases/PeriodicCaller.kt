package com.marcinmoskala.kotlinacademy.usecases

import com.marcinmoskala.kotlinacademy.common.Cancellable
import com.marcinmoskala.kotlinacademy.common.Provider
import com.marcinmoskala.kotlinacademy.common.delay
import com.marcinmoskala.kotlinacademy.common.launchUI

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