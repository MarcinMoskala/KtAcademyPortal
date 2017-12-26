package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.repositories.network.dto.NotificationResult
import org.kotlinacademy.data.Feedback

suspend fun sendEmailWithInfoAboutFeedback(feedback: Feedback, emailRepository: EmailRepository, databaseRepository: DatabaseRepository) {
    val adminEmail = Config.adminEmail ?: return
    val feedbackTo = feedback.newsId?.let { databaseRepository.getNews(it) }?.title ?: "Kotlin Academy"
    emailRepository.sendEmail(
            to = adminEmail,
            title = "New feedback",
            message = """
                New feedback to $feedbackTo
                Rating: ${feedback.rating}
                Comment:
                ${feedback.comment}

                Suggestions:
                ${feedback.suggestions}
            """.trimIndent()
    )
}

suspend fun sendEmailWithNotificationResult(result: NotificationResult, emailRepository: EmailRepository) {
    val adminEmail = Config.adminEmail ?: return
    emailRepository.sendEmail(
            to = adminEmail,
            title = "Notification report",
            message = """
                Success: ${result.success}
                Failure: ${result.failure}
            """.trimIndent()
    )
}