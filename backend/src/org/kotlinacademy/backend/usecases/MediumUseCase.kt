package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.logInfo
import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.repositories.network.MediumRepository
import org.kotlinacademy.backend.repositories.network.NotificationsRepository

suspend fun syncWithMedium(mediumRepository: MediumRepository, databaseRepository: DatabaseRepository, notificationsRepository: NotificationsRepository?, emailRepository: EmailRepository?) {
    val news = mediumRepository.getNews()
    if (news == null) {
        logInfo("Medium did not succeed when processing request")
        return
    }

    val prevNewsTitles = databaseRepository.getNews().map { it.title }
    news.filter { it.title !in prevNewsTitles }
            .forEach { addNews(it, databaseRepository, notificationsRepository, emailRepository) }
}