package com.marcinmoskala.kotlinacademy.ui.view

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.marcinmoskala.kotlinacademy.presentation.Presenter

abstract class BaseActivity : AppCompatActivity() {

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
}