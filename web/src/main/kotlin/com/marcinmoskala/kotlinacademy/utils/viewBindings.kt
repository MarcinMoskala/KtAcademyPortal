package com.marcinmoskala.kotlinacademy.utils

import react.RComponent
import react.setState
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

fun <T> bindToStateProperty(mutableProperty: KMutableProperty0<T>): ReadWriteProperty<RComponent<*, *>, T>
        = BindToPropertyImpl(mutableProperty)

class BindToPropertyImpl<T>(val mutableProperty: KMutableProperty0<T>) : ReadWriteProperty<RComponent<*, *>, T> {

    override fun getValue(thisRef: RComponent<*, *>, property: KProperty<*>): T = mutableProperty.get()

    override fun setValue(thisRef: RComponent<*, *>, property: KProperty<*>, value: T) {
        thisRef.setState {
            mutableProperty.set(value)
        }
    }
}
