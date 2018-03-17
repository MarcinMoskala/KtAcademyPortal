package org.kotlinacademy.backend

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.kotlinacademy.backend.repositories.db.ArticlesDatabaseRepository
import org.kotlinacademy.backend.repositories.db.TokenDatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.repositories.network.MediumRepository
import org.kotlinacademy.backend.repositories.network.NotificationsRepository
import org.kotlinacademy.backend.usecases.MediumUseCase
import java.util.concurrent.TimeUnit

fun launchSyncJobs() {
    val mediumRepository by MediumRepository.lazyGet()
    val articlesDatabaseRepository by ArticlesDatabaseRepository.lazyGet()
    val tokenDatabaseRepository by TokenDatabaseRepository.lazyGet()
    val emailRepository by EmailRepository.lazyGet()
    val notificationsRepository by NotificationsRepository.lazyGet()

    val interval = Config.mediumRefreshIntervalInMinutes ?: return
    launchEvery(interval, TimeUnit.MINUTES) {
        MediumUseCase.sync(mediumRepository, articlesDatabaseRepository)
    }
}

private fun launchEvery(interval: Long, unit: TimeUnit, block: suspend CoroutineScope.() -> Unit) {
    launch(CommonPool) {
        while (true) {
            block()
            delay(interval, unit)
        }
    }
}