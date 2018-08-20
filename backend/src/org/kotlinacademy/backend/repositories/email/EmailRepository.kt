package org.kotlinacademy.backend.repositories.email

import com.sendgrid.*
import kotlinx.coroutines.experimental.async
import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.common.Provider
import org.kotlinacademy.backend.errors.MissingElementError
import org.kotlinacademy.backend.logInfo
import java.io.IOException

interface EmailRepository {

    suspend fun sendEmail(to: String, title: String, message: String)

    suspend fun sendHtmlEmail(to: String, title: String, message: String)

    class EmailRepositoryImpl : EmailRepository {
        private val sendGrid = SendGrid(Config.emailApiToken)

        override suspend fun sendEmail(to: String, title: String, message: String) {
            sendEmail("text/plain", to, title, message)
        }

        override suspend fun sendHtmlEmail(to: String, title: String, message: String) {
            sendEmail("text/html", to, title, message)
        }

        private suspend fun sendEmail(contentType: String, to: String, title: String, message: String) {
            val content = Content(contentType, message)
            val mail = Mail(Email("info@kotlinacademy.org"), title, Email(to), content)
            try {
                val request = Request().apply {
                    method = Method.POST
                    endpoint = "mail/send"
                    body = mail.build()
                }
                val response = async { sendGrid.api(request) }.await()
                logResponse(response)
            } catch (e: IOException) {
                throw e
            }
        }

        private fun logResponse(response: Response) {
            logInfo("Message send")
            logInfo(response.statusCode.toString())
            logInfo(response.body)
            logInfo(response.headers.toString())
        }
    }

    companion object : Provider<EmailRepository>() {
        override fun create() = if (Config.emailApiToken != null) {
            EmailRepositoryImpl()
        } else {
            throw MissingElementError("EmailRepository env var")
        }
    }
}