package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.repositories.db.ArticlesDatabaseRepository
import org.kotlinacademy.backend.repositories.db.InfoDatabaseRepository
import org.kotlinacademy.backend.repositories.db.PuzzlersDatabaseRepository
import org.kotlinacademy.backend.repositories.network.NotificationsRepository
import org.kotlinacademy.data.Article
import org.kotlinacademy.data.Info
import org.kotlinacademy.data.Puzzler
import org.kotlinacademy.now

object NewsUseCase {

    suspend fun propose(info: Info) {
        val infoDatabaseRepository = InfoDatabaseRepository.get()
        infoDatabaseRepository.addInfo(info, false)
        EmailUseCase.askForAcceptation(info)
    }

    suspend fun propose(puzzler: Puzzler) {
        val puzzlersDatabaseRepository = PuzzlersDatabaseRepository.get()
        puzzlersDatabaseRepository.addPuzzler(puzzler, false)
        EmailUseCase.askForAcceptation(puzzler)
    }

    suspend fun acceptInfo(id: Int) {
        val infoDatabaseRepository = InfoDatabaseRepository.get()
        val notificationsRepository = NotificationsRepository.get()

        val info = infoDatabaseRepository.getInfo(id)
        val changedInfo = info.copy(dateTime = now)
        infoDatabaseRepository.updateInfo(id, changedInfo, true)
        if (notificationsRepository != null) {
            val title = "New info: " + info.title
            val url = Config.baseUrl // TODO: To particular info
            NotificationsUseCase.send(title, url)
        }
    }

    suspend fun acceptPuzzler(id: Int) {
        val notificationsRepository = NotificationsRepository.get()
        val puzzlersDatabaseRepository = PuzzlersDatabaseRepository.get()

        val puzzler = puzzlersDatabaseRepository.getPuzzler(id)
        val changedPuzzler = puzzler.copy(dateTime = now)
        puzzlersDatabaseRepository.updatePuzzler(id, changedPuzzler, true)
        if (notificationsRepository != null) {
            val title = "New puzzler: " + puzzler.title
            val url = Config.baseUrl // TODO: To particular puzzler
            NotificationsUseCase.send(title, url)
        }
    }
}