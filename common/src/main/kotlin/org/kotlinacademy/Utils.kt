package org.kotlinacademy

fun String.kebabCase(): String = toLowerCase().replace("\\s".toRegex(), "-")
