package org.kotlinacademy.presentation

import org.kotlinacademy.common.Job

abstract class BasePresenter : Presenter {

    protected var jobs: List<Job> = emptyList()

    override fun onDestroy() {
        jobs.filter { !it.isCancelled }.forEach { it.cancel(Error("BasePresenter.onDestroy")) }
    }
}