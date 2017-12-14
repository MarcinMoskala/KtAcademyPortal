package org.kotlinacademy.backend.repositories.email

import com.sendgrid.*
import kotlinx.coroutines.experimental.async
import org.kotlinacademy.backend.logInfo
import org.kotlinacademy.common.Provider
import java.io.IOException

interface EmailRepository {

    suspend fun sendEmail(to: String, title: String, message: String)

    class EmailRepositoryImpl : EmailRepository {
        suspend override fun sendEmail(to: String, title: String, message: String) {
            val from = Email("info@kotlinacademy.org")
            val to = Email(to)
            val content = Content("text/plain", message)
            val mail = Mail(from, title, to, content)

            val sg = SendGrid(System.getenv("SENDGRID_API_KEY"))
            val request = Request()
            try {
                request.method = Method.POST
                request.endpoint = "mail/send"
                request.body = mail.build()
                val response = async { sg.api(request) }.await()
                logInfo("Message send")
                logInfo(response.statusCode.toString())
                logInfo(response.body)
                logInfo(response.headers.toString())
            } catch (ex: IOException) {
                throw ex
            }
        }
    }

    companion object : Provider<EmailRepository>() {
        override fun create(): EmailRepository = EmailRepositoryImpl()

    }
}