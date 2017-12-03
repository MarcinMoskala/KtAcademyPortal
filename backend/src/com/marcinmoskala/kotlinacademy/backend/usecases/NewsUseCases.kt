package com.marcinmoskala.kotlinacademy.backend.usecases

import com.marcinmoskala.kotlinacademy.backend.repositories.db.DatabaseRepository
import com.marcinmoskala.kotlinacademy.backend.repositories.network.NotificationsRepository
import com.marcinmoskala.kotlinacademy.data.News

suspend fun addOrUpdateNews(news: News, databaseRepo: DatabaseRepository, notificationsRepository: NotificationsRepository?) {
    val id = news.id
    if (id == null) {
        addNews(databaseRepo, news, notificationsRepository)
    } else {
        databaseRepo.updateNews(id, news)
    }
}

private suspend fun addNews(databaseRepo: DatabaseRepository, news: News, notificationsRepository: NotificationsRepository?) {
    databaseRepo.addNews(news)
    if (notificationsRepository != null) {
        sendNotification("New article: " + news.title, databaseRepo, notificationsRepository)
    }
}

suspend fun getAllNews(databaseRepo: DatabaseRepository) = databaseRepo.getNews()