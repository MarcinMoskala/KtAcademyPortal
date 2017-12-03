package com.marcinmoskala.kotlinacademy.backend.usecases

import com.marcinmoskala.kotlinacademy.backend.repositories.db.DatabaseRepository
import com.marcinmoskala.kotlinacademy.backend.repositories.network.NotificationsRepository
import com.marcinmoskala.kotlinacademy.data.News

suspend fun addOrUpdateNews(news: News, databaseRepo: DatabaseRepository, notificationsRepository: NotificationsRepository?) {
    val id = news.id
    if (id == null) {
        addNews(news, databaseRepo, notificationsRepository)
    } else {
        databaseRepo.updateNews(id, news)
    }
}

suspend fun addNews(news: News, databaseRepo: DatabaseRepository, notificationsRepository: NotificationsRepository?) {
    databaseRepo.addNews(news)
    if (notificationsRepository != null) {
        sendNotifications("New article: " + news.title, databaseRepo, notificationsRepository)
    }
}

suspend fun getAllNews(databaseRepo: DatabaseRepository) = databaseRepo.getNews()