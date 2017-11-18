package com.marcinmoskala.kotlinacademy.ui.common

fun String?.nullIfBlank(): String? = if(this == null || isBlank()) null else this
