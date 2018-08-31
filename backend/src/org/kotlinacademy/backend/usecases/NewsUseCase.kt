package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.repositories.db.ArticlesDatabaseRepository
import org.kotlinacademy.backend.repositories.db.InfoDatabaseRepository
import org.kotlinacademy.backend.repositories.db.PuzzlersDatabaseRepository
import org.kotlinacademy.backend.repositories.db.SnippetDatabaseRepository
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
        val snippetDatabaseRepository by SnippetDatabaseRepository.lazyGet()

        val articles = articlesDatabaseRepository.getArticles()
        val infos = infoDatabaseRepository.getInfos()
        val puzzlers = puzzlersDatabaseRepository.getPuzzlers()
        val snippets = snippetDatabaseRepository.getSnippets()
        return NewsData(
                articles = articles,
                infos = infos.filter { it.accepted },
                puzzlers = puzzlers.filter { it.accepted },
                snippets = snippets.filter { it.accepted }
        )
    }

    suspend fun getPropositions(): NewsData {
        val infoDatabaseRepository by InfoDatabaseRepository.lazyGet()
        val puzzlersDatabaseRepository by PuzzlersDatabaseRepository.lazyGet()
        val snippetDatabaseRepository by SnippetDatabaseRepository.lazyGet()

        val infos = infoDatabaseRepository.getInfos()
        val puzzlers = puzzlersDatabaseRepository.getPuzzlers()
        val snippets = snippetDatabaseRepository.getSnippets()
        return NewsData(
                articles = emptyList(),
                infos = infos.filter { !it.accepted },
                puzzlers = puzzlers.filter { !it.accepted },
                snippets = snippets.filter { !it.accepted }
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

    suspend fun propose(snippetData: SnippetData) {
        val snippetDatabaseRepository = SnippetDatabaseRepository.get()
        snippetDatabaseRepository.addSnippet(snippetData, false)
    }

    suspend fun update(info: Info) {
        val infoDatabaseRepository = InfoDatabaseRepository.get()

        infoDatabaseRepository.updateInfo(info)
    }

    suspend fun update(puzzler: Puzzler) {
        val puzzlersDatabaseRepository = PuzzlersDatabaseRepository.get()

        puzzlersDatabaseRepository.updatePuzzler(puzzler)
    }

    suspend fun update(snippet: Snippet) {
        val snippetDatabaseRepository = SnippetDatabaseRepository.get()

        snippetDatabaseRepository.updateSnippet(snippet)
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

    suspend fun acceptSnippet(id: Int) {
        val snippetDatabaseRepository = SnippetDatabaseRepository.get()

        val snippet = snippetDatabaseRepository.getSnippet(id)
        val changedSnippet = snippet.copy(dateTime = now, accepted = true)
        snippetDatabaseRepository.updateSnippet(changedSnippet)
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

    suspend fun deleteSnippet(id: Int) {
        val snippetDatabaseRepository = SnippetDatabaseRepository.get()

        snippetDatabaseRepository.deleteSnippet(id)
    }
}