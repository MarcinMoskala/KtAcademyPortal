package com.marcinmoskala.kotlinacademy.ui.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.marcinmoskala.kotlinacademy.BuildConfig
import com.marcinmoskala.kotlinacademy.common.HttpError
import com.marcinmoskala.kotlinacademy.presentation.BaseView
import com.marcinmoskala.kotlinacademy.presentation.Presenter
import com.marcinmoskala.kotlinacademy.ui.common.toast

abstract class BaseActivity : AppCompatActivity(), BaseView {

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
        logError(error)
        val message = if (error is HttpError) {
            "Http error! Code: ${error.code} Message: ${error.message}"
        } else {
            "Error ${error.message}"
        }
        toast(message)
    }

    override fun logError(error: Throwable) {
        if(BuildConfig.DEBUG) Log.e(this::class.simpleName, error.message, error)
    }
}