package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.Config.baseUrl
import org.kotlinacademy.backend.errors.MissingElementError
import org.kotlinacademy.backend.repositories.db.ArticlesDatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.repositories.network.notifications.NotificationResult
import org.kotlinacademy.data.*

object EmailUseCase {

    suspend fun sendInfoAboutFeedback(feedback: Feedback) {
        val articlesDatabaseRepository = ArticlesDatabaseRepository.get()
        val emailRepository = EmailRepository.get()
        val articleTitle = feedback.newsId?.let { articlesDatabaseRepository.getArticle(it) }?.title
        val feedbackTo = articleTitle ?: "Kotlin Academy"
        emailRepository.emailToAdmin("New feedback", """
                |New feedback to $feedbackTo <br>
                |Rating: ${feedback.rating} <br>
                |Comment: <br>
                |${feedback.comment} <br>
                |<br>
                |Suggestions: <br>
                |${feedback.suggestions}
            """)
    }

    suspend fun sendNotificationResult(result: NotificationResult) {
        val emailRepository = EmailRepository.get()
        emailRepository.emailToAdmin("Notification report", """
                |Success: ${result.success} <br>
                |Failure: ${result.failure}
            """)
    }

    suspend fun askForAcceptation(info: Info) {
        val emailRepository = EmailRepository.get()
        emailRepository.emailToAdmin("Request for info acceptation", """
                |Title: ${info.title} <br>
                |Description: ${info.description} <br>
                |Image: <img src="${info.imageUrl}"> <br>
                |Sources: <img src="${info.sources}"> <br>
                |URL: ${info.url} <br>
                |Author: ${info.author} <br>
                |Author URL: ${info.authorUrl} <br>
                |Occurrence: ${info.dateTime.toDateFormatString()} <br>
                |${makeManagerButton()}
            """)
    }

    private fun makeManagerButton() =
            """$baseUrl/#/manager?secret=${Config.secretHash} <br>""".trimMargin()

    private suspend fun EmailRepository.emailToAdmin(title: String, textHtml: String) {
        val adminEmail = Config.adminEmail ?: throw MissingElementError("ADMIN_EMAIL env var")
        val message = textHtml.trimMargin()
        sendHtmlEmail(adminEmail, title, message)
    }
}