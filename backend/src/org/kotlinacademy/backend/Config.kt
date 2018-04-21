package org.kotlinacademy.backend

import java.security.MessageDigest

object Config {
    private val config = application?.environment?.config

    val production = config?.config("service")?.property("environment")?.getString() == "production"

    private val secret
            = System.getenv("SERVER_SECRET").takeUnless { it.isNullOrBlank() } ?: "XXX"

    var adminEmail = System.getenv("ADMIN_EMAIL").takeUnless { it.isNullOrBlank() }

    val baseUrl = System.getenv("BASE_URL") ?: "http://localhost:8088/"

    val emailApiToken = System.getenv("SENDGRID_API_KEY").takeUnless { it.isNullOrBlank() }

    val secretHash = sha1(secret)

    val firebaseSecretApiKey: String?
            = System.getenv("SECRET_FIREBASE_KEY").takeUnless { it.isNullOrBlank() }

    val mediumRefreshIntervalInMinutes = config
            ?.config("medium")
            ?.property("intervalInMinutes")
            ?.getString()
            ?.toLong()

    val mediumToken: String = System.getenv("MEDIUM_TOKEN_KEY") ?: ""

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
}