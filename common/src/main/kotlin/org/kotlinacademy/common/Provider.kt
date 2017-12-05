package org.kotlinacademy.common

abstract class Provider<T> {

    private var original: T? = null
    var override: T? = null

    abstract fun create(): T

    fun get(): T = override ?: original ?: create().apply { original = this }
    fun lazyGet(): Lazy<T> = lazy { get() }
}