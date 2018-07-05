package org.kotlinacademy.backend.common

abstract class Provider<T> {

    private val original by lazy { create() }
    var mock: T? = null

    abstract fun create(): T

    fun get(): T = mock ?: original
    fun lazyGet(): Lazy<T> = lazy { get() }
}