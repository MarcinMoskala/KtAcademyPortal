package org.kotlinacademy.backend

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.repositories.network.MediumRepository
import org.kotlinacademy.backend.repositories.network.NotificationsRepository
import org.kotlinacademy.backend.usecases.syncWithMedium
import java.util.concurrent.TimeUnit

fun launchSyncJobs() {
    val mediumRepository by MediumRepository.lazyGet()
    val databaseRepository by DatabaseRepository.lazyGet()
    val notificationsRepository by NotificationsRepository.lazyGet()
    val emailRepository by EmailRepository.lazyGet()

    val interval = Config.mediumRefreshIntervalInMinutes ?: return
    launchEvery(interval, TimeUnit.MINUTES) {
        syncWithMedium(mediumRepository, databaseRepository, notificationsRepository, emailRepository)
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