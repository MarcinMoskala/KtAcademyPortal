package com.marcinmoskala.kotlinacademy.backend

import java.security.MessageDigest

// TODO Move it to gradle.properties
// Use different secret for production
private val secret = "XXX"
val secretHash = sha1(secret)

private fun sha1(clearString: String) = try {
    val md = MessageDigest.getInstance("SHA-1")
    val byteArray = md.digest(clearString.toByteArray())
    byteArrayToHexString(byteArray)
} catch (e: Exception) {
    e.printStackTrace()
    throw e
}

private fun byteArrayToHexString(b: ByteArray) = b.joinToString(
        separator = "",
        transform = { byte -> Integer.toString((byte.toInt() and 0xFF) + 0x100, 16).substring(1) }
)

fun main(args: Array<String>) {
    println(secretHash)
}