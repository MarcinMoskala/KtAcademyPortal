package org.kotlinacademy.wear.view

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import org.kotlinacademy.common.HttpError
import org.kotlinacademy.common.toast
import org.kotlinacademy.presentation.BaseView
import org.kotlinacademy.presentation.Presenter

abstract class WearableBaseActivity : WearableActivity(), BaseView {

    protected fun <T : Presenter> presenter(init: () -> T) = lazy(init)
            .also { lazyPresenters += it }

    private var lazyPresenters: List<Lazy<Presenter>> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lazyPresenters.forEach { it.value.onCreate() }
    }

    override fun onDestroy() {
        super.onDestroy()
        lazyPresenters.forEach { it.value.onDestroy() }
    }

    override fun showError(error: Throwable) {
        val message = if (error is HttpError) {
            "Http error! Code: ${error.code} Message: ${error.message}"
        } else {
            "Error ${error.message}"
        }
        toast(message)
    }

    override fun logError(error: Throwable) {
        error.printStackTrace()
    }
}