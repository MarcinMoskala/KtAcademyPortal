package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.repositories.network.NotificationsRepository
import org.kotlinacademy.data.News

suspend fun addOrUpdateNews(news: News, databaseRepo: DatabaseRepository, notificationsRepository: NotificationsRepository?, emailRepository: EmailRepository?) {
    val id = news.id
    if (id == null) {
        addNews(news, databaseRepo, notificationsRepository, emailRepository)
    } else {
        databaseRepo.updateNews(id, news)
    }
}

suspend fun addNews(news: News, databaseRepo: DatabaseRepository, notificationsRepository: NotificationsRepository?, emailRepository: EmailRepository?) {
    databaseRepo.addNews(news)
    if (notificationsRepository != null) {
        val title = "New article: " + news.title
        val url = news.url ?: "https://blog.kotlin-academy.com/"
        sendNotifications(title, url, databaseRepo, notificationsRepository, emailRepository)
    }
}

suspend fun getAllNews(databaseRepo: DatabaseRepository) = databaseRepo.getNews()