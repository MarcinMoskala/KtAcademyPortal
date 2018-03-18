package org.kotlinacademy.backend.usecases

import org.kotlinacademy.Endpoints
import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.repositories.db.ArticlesDatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.repositories.network.dto.NotificationResult
import org.kotlinacademy.data.Article
import org.kotlinacademy.data.Feedback
import org.kotlinacademy.data.Info
import org.kotlinacademy.data.Puzzler

object EmailUseCase {

    suspend fun sendInfoAboutFeedback(feedback: Feedback) {
        val articlesDatabaseRepository = ArticlesDatabaseRepository.get()
        val emailRepository = EmailRepository.get() ?: return
        val articleTitle = feedback.newsId?.let { articlesDatabaseRepository.getArticle(it) }?.title
        val feedbackTo = articleTitle ?: "Kotlin Academy"
        emailRepository.emailToAdmin("New feedback", """
                |New feedback to $feedbackTo
                |Rating: ${feedback.rating}
                |Comment:
                |${feedback.comment}
                |
                |Suggestions:
                |${feedback.suggestions}
            """)
    }

    suspend fun sendNotificationResult(result: NotificationResult) {
        val emailRepository = EmailRepository.get() ?: return
        emailRepository.emailToAdmin("Notification report", """
                |Success: ${result.success}
                |Failure: ${result.failure}
            """)
    }

    suspend fun askForAcceptation(article: Article) {
        val emailRepository = EmailRepository.get() ?: return
        emailRepository.emailToAdmin("Request for article acceptation", """
                |Title: ${article.title}
                |Subtitle: ${article.subtitle}
                |Image: <img src="${article.imageUrl}">
                |URL: ${article.url}
                |Occurrence: ${article.occurrence.toDateFormatString()}
                |${makeButtons(article.id, Endpoints.article)}
            """)
    }

    suspend fun askForAcceptation(info: Info) {
        val emailRepository = EmailRepository.get() ?: return
        emailRepository.emailToAdmin("Request for info acceptation", """
                |Title: ${info.title}
                |Description: ${info.description}
                |Image: <img src="${info.imageUrl}">
                |Sources: <img src="${info.sources}">
                |URL: ${info.url}
                |Author: ${info.author}
                |Author URL: ${info.authorUrl}
                |Occurrence: ${info.dateTime.toDateFormatString()}
                |${makeButtons(info.id, Endpoints.info)}
            """)
    }

    suspend fun askForAcceptation(puzzler: Puzzler) {
        val emailRepository = EmailRepository.get() ?: return
        emailRepository.emailToAdmin("Request for article acceptation", """
                |Title: ${puzzler.title}
                |Question: ${puzzler.question}
                |Answers: <img src="${puzzler.answers}">
                |Author: ${puzzler.author}
                |Author URL: ${puzzler.authorUrl}
                |Addet at: ${puzzler.dateTime.toDateFormatString()}
                |${makeButtons(puzzler.id, Endpoints.puzzler)}
            """)
    }

    private fun makeButtons(id: Int, type: String) =
            """<a href="${Config.baseUrl}/$type/$id/${Endpoints.accept}?secret-hash=${Config.secretHash}">Accept</a>
                |<a href="${Config.baseUrl}/$type/$id/${Endpoints.reject}?secret-hash=${Config.secretHash}">Reject</a>""".trimMargin()

    private suspend fun EmailRepository.emailToAdmin(title: String, text: String) {
        val adminEmail = Config.adminEmail ?: return
        sendEmail(
                to = adminEmail,
                title = title,
                message = text.trimMargin()
        )
    }
}