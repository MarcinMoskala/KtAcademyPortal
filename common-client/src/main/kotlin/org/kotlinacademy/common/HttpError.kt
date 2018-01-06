package org.kotlinacademy.common

class HttpError(
        val code: Int,
        override val message: String
) : Throwable()