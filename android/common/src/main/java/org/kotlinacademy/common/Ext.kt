package org.kotlinacademy.common

fun String?.nullIfBlank(): String? = if(this == null || isBlank()) null else this
