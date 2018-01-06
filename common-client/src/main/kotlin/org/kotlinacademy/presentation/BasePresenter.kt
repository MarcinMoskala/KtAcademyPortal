package org.kotlinacademy.presentation

import org.kotlinacademy.common.Cancellable

abstract class BasePresenter : Presenter {

    protected var jobs: List<Cancellable> = emptyList()

    override fun onDestroy() {
        jobs.forEach { it.cancel() }
    }
}