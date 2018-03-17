package org.kotlinacademy.backend.repositories.email

import com.sendgrid.*
import kotlinx.coroutines.experimental.async
import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.logInfo
import org.kotlinacademy.common.Provider
import java.io.IOException

interface EmailRepository {

    suspend fun sendEmail(to: String, title: String, message: String)

    class EmailRepositoryImpl : EmailRepository {
        private val sendGrid = SendGrid(Config.emailApiToken)

        override suspend fun sendEmail(to: String, title: String, message: String) {
            val from = Email("info@kotlinacademy.org")
            val content = Content("text/plain", message)
            val mail = Mail(from, title, Email(to), content)
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

    companion object : Provider<EmailRepository?>() {
        override fun create() = if (Config.emailApiToken != null) {
            logInfo("I create EmailRepository")
            EmailRepositoryImpl()
        } else {
            logInfo("No emailApiToken")
            null
        }
    }
}