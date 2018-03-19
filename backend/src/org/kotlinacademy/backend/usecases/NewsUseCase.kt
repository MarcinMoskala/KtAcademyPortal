package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.repositories.db.InfoDatabaseRepository
import org.kotlinacademy.backend.repositories.db.PuzzlersDatabaseRepository
import org.kotlinacademy.backend.repositories.network.NotificationsRepository
import org.kotlinacademy.data.InfoData
import org.kotlinacademy.data.PuzzlerData
import org.kotlinacademy.data.title
import org.kotlinacademy.now

object NewsUseCase {

    suspend fun propose(infoData: InfoData) {
        val infoDatabaseRepository = InfoDatabaseRepository.get()
        val info = infoDatabaseRepository.addInfo(infoData, false)
        EmailUseCase.askForAcceptation(info)
    }

    suspend fun propose(puzzlerData: PuzzlerData) {
        val puzzlersDatabaseRepository = PuzzlersDatabaseRepository.get()
        val puzzler = puzzlersDatabaseRepository.addPuzzler(puzzlerData, false)
        EmailUseCase.askForAcceptation(puzzler)
    }

    suspend fun acceptInfo(id: Int) {
        val infoDatabaseRepository = InfoDatabaseRepository.get()
        val notificationsRepository = NotificationsRepository.get()

        val info = infoDatabaseRepository.getInfo(id)
        val changedInfo = info.copy(dateTime = now, accepted = true)
        infoDatabaseRepository.updateInfo(changedInfo)
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
        val changedPuzzler = puzzler.copy(dateTime = now, accepted = true)
        puzzlersDatabaseRepository.updatePuzzler(changedPuzzler)
        if (notificationsRepository != null) {
            val title = "New puzzler: " + puzzler.title
            val url = Config.baseUrl // TODO: To particular puzzler
            NotificationsUseCase.send(title, url)
        }
    }
}