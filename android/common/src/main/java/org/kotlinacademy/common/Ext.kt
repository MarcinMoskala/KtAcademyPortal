package org.kotlinacademy.common

fun String?.nullIfBlank(): String? = if(this == null || isBlank()) null else this

inline fun <T1, T2, reified RT2: T2> List<Pair<T1, T2>>.filterSecondIs(): List<Pair<T1, RT2>>
        = filter { it.second is RT2 }.filterIsInstance<Pair<T1, RT2>>()