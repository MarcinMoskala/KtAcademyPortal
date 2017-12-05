package com.marcinmoskala.kotlinacademy.components

import com.marcinmoskala.kotlinacademy.presentation.BaseView
import com.marcinmoskala.kotlinacademy.presentation.Presenter
import react.RComponent

abstract class BaseComponent<P : react.RProps, S : BaseState> : RComponent<P, S>(), BaseView {

    protected fun <T : Presenter> presenter(init: () -> T) = lazy(init)
            .also { lazyPresenters += it }

    private var lazyPresenters: List<Lazy<Presenter>> = emptyList()

    override fun componentDidMount() {
        lazyPresenters.forEach { it.value.onCreate() }
    }

    override fun componentWillUnmount() {
        lazyPresenters.forEach { it.value.onDestroy() }
    }

    override fun showError(error: Throwable) {
        println(error)
        setState { this.error = error }
    }

    override fun logError(error: Throwable) {
        console.log(error)
    }
}