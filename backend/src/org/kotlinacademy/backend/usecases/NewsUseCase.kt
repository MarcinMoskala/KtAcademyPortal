package org.kotlinacademy.backend.usecases

import org.kotlinacademy.DateTime
import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.common.isToday
import org.kotlinacademy.backend.repositories.db.ArticlesDatabaseRepository
import org.kotlinacademy.backend.repositories.db.InfoDatabaseRepository
import org.kotlinacademy.backend.repositories.db.PuzzlersDatabaseRepository
import org.kotlinacademy.data.*
import org.kotlinacademy.minus
import org.kotlinacademy.now

object NewsUseCase {

    suspend fun addArticle(articleData: ArticleData) {
        val articlesDatabaseRepository = ArticlesDatabaseRepository.get()

        articlesDatabaseRepository.addArticle(articleData)
        NotificationsUseCase.sendToAll("New article ${articleData.title}", Config.baseUrl)
    }

    suspend fun deleteArticle(articleId: Int) {
        val articlesDatabaseRepository = ArticlesDatabaseRepository.get()

        articlesDatabaseRepository.deleteArticle(articleId)
    }

    suspend fun getAcceptedNewsData(): NewsData {
        val articlesDatabaseRepository by ArticlesDatabaseRepository.lazyGet()
        val infoDatabaseRepository by InfoDatabaseRepository.lazyGet()
        val puzzlersDatabaseRepository by PuzzlersDatabaseRepository.lazyGet()

        val articles = articlesDatabaseRepository.getArticles()
        val infos = infoDatabaseRepository.getInfos()
        val puzzlers = puzzlersDatabaseRepository.getPuzzlers()
        return NewsData(
                articles = articles,
                infos = infos.filter { it.accepted },
                puzzlers = puzzlers.filter { it.accepted }
        )
    }

    suspend fun getPropositions(): NewsData {
        val infoDatabaseRepository by InfoDatabaseRepository.lazyGet()
        val puzzlersDatabaseRepository by PuzzlersDatabaseRepository.lazyGet()

        val infos = infoDatabaseRepository.getInfos()
        val puzzlers = puzzlersDatabaseRepository.getPuzzlers()
        return NewsData(
                articles = emptyList(),
                infos = infos.filter { !it.accepted },
                puzzlers = puzzlers.filter { !it.accepted }
        )
    }

    suspend fun propose(infoData: InfoData) {
        val infoDatabaseRepository = InfoDatabaseRepository.get()

        val info = infoDatabaseRepository.addInfo(infoData, false)
        EmailUseCase.askForAcceptation(info)
    }

    suspend fun propose(puzzlerData: PuzzlerData) {
        val puzzlersDatabaseRepository = PuzzlersDatabaseRepository.get()
        puzzlersDatabaseRepository.addPuzzler(puzzlerData, false)
    }

    suspend fun update(info: Info) {
        val infoDatabaseRepository = InfoDatabaseRepository.get()

        infoDatabaseRepository.updateInfo(info)
    }

    suspend fun update(puzzler: Puzzler) {
        val puzzlersDatabaseRepository = PuzzlersDatabaseRepository.get()

        puzzlersDatabaseRepository.updatePuzzler(puzzler)
    }

    suspend fun acceptInfo(id: Int) {
        val infoDatabaseRepository = InfoDatabaseRepository.get()

        val info = infoDatabaseRepository.getInfo(id)
        val changedInfo = info.copy(dateTime = now, accepted = true)
        infoDatabaseRepository.updateInfo(changedInfo)

        NotificationsUseCase.sendToAll(
                body = "New info: " + info.title,
                url = Config.baseUrl // TODO: To particular info
        )
    }

    suspend fun acceptPuzzler(id: Int) {
        val puzzlersDatabaseRepository = PuzzlersDatabaseRepository.get()

        val puzzler = puzzlersDatabaseRepository.getPuzzler(id)
        val changedPuzzler = puzzler.copy(dateTime = now, accepted = true)
        puzzlersDatabaseRepository.updatePuzzler(changedPuzzler)
    }

    suspend fun acceptImportantPuzzler(id: Int) {
        acceptPuzzler(id)
        sendNotificationsAboutPuzzler(id)
    }

    suspend fun movePuzzlerTop(id: Int) {
        val puzzlersDatabaseRepository = PuzzlersDatabaseRepository.get()
        val puzzlers = puzzlersDatabaseRepository.getPuzzlers()
        val lastPuzzlerDateTime = puzzlers.map { it.dateTime }.min() ?: return
        val puzzler = puzzlersDatabaseRepository.getPuzzler(id)
        val changedPuzzler = puzzler.copy(dateTime = lastPuzzlerDateTime - 1)
        puzzlersDatabaseRepository.updatePuzzler(changedPuzzler)
    }

    suspend fun  unpublishPuzzler(id: Int) {
        val puzzlersDatabaseRepository = PuzzlersDatabaseRepository.get()

        val puzzler = puzzlersDatabaseRepository.getPuzzler(id)
        val changedPuzzler = puzzler.copy(accepted = false)
        puzzlersDatabaseRepository.updatePuzzler(changedPuzzler)
    }

    private suspend fun sendNotificationsAboutPuzzler(id: Int) {
        val puzzlersDatabaseRepository = PuzzlersDatabaseRepository.get()
        val puzzler = puzzlersDatabaseRepository.getPuzzler(id)
        NotificationsUseCase.sendToAll(
                body = "New puzzler: " + puzzler.title,
                url = Config.baseUrl // TODO: To particular puzzler
        )
    }

    suspend fun deleteInfo(id: Int) {
        val infoDatabaseRepository = InfoDatabaseRepository.get()

        infoDatabaseRepository.deleteInfo(id)
    }

    suspend fun deletePuzzler(id: Int) {
        val puzzlersDatabaseRepository = PuzzlersDatabaseRepository.get()

        puzzlersDatabaseRepository.deletePuzzler(id)
    }
}