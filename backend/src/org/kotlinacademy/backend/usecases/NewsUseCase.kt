package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.repositories.db.ArticlesDatabaseRepository
import org.kotlinacademy.backend.repositories.db.InfoDatabaseRepository
import org.kotlinacademy.backend.repositories.db.PuzzlersDatabaseRepository
import org.kotlinacademy.backend.repositories.db.TokenDatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.repositories.network.NotificationsRepository
import org.kotlinacademy.data.Article
import org.kotlinacademy.data.Info
import org.kotlinacademy.data.Puzzler
import org.kotlinacademy.now

object NewsUseCase {

    suspend fun addOrUpdate(article: Article, articlesDatabaseRepo: ArticlesDatabaseRepository, emailRepository: EmailRepository?) {
        if (article.id == -1) {
            NewsUseCase.propose(article, articlesDatabaseRepo, emailRepository)
        } else {
            articlesDatabaseRepo.updateArticle(article.id, article)
        }
    }

    suspend fun propose(article: Article, articlesDatabaseRepository: ArticlesDatabaseRepository, emailRepository: EmailRepository?) {
        articlesDatabaseRepository.addArticle(article, false)
        emailRepository ?: return
        EmailUseCase.askForAcceptation(article, emailRepository)
    }

    suspend fun propose(info: Info, infoDatabaseRepository: InfoDatabaseRepository, emailRepository: EmailRepository?) {
        infoDatabaseRepository.addInfo(info, false)
        emailRepository ?: return
        EmailUseCase.askForAcceptation(info, emailRepository)
    }

    suspend fun propose(puzzler: Puzzler, puzzlersDatabaseRepository: PuzzlersDatabaseRepository, emailRepository: EmailRepository?) {
        puzzlersDatabaseRepository.addPuzzler(puzzler, false)
        emailRepository ?: return
        EmailUseCase.askForAcceptation(puzzler, emailRepository)
    }

    suspend fun acceptArticle(id: Int, articlesDatabaseRepository: ArticlesDatabaseRepository, tokenDatabaseRepository: TokenDatabaseRepository, notificationsRepository: NotificationsRepository?, emailRepository: EmailRepository?) {
        val article = articlesDatabaseRepository.getArticle(id)
        articlesDatabaseRepository.updateArticle(id, article, true)
        if (notificationsRepository != null) {
            val title = "New article: " + article.title
            val url = article.url ?: Config.baseUrl
            NotificationsUseCase.send(title, url, tokenDatabaseRepository, notificationsRepository, emailRepository)
        }
    }

    suspend fun acceptInfo(id: Int, infoDatabaseRepository: InfoDatabaseRepository, tokenDatabaseRepository: TokenDatabaseRepository, notificationsRepository: NotificationsRepository?, emailRepository: EmailRepository?) {
        val info = infoDatabaseRepository.getInfo(id)
        val changedInfo = info.copy(dateTime = now)
        infoDatabaseRepository.updateInfo(id, changedInfo, true)
        if (notificationsRepository != null) {
            val title = "New info: " + info.title
            val url = Config.baseUrl // TODO: To particular info
            NotificationsUseCase.send(title, url, tokenDatabaseRepository, notificationsRepository, emailRepository)
        }
    }

    suspend fun acceptPuzzler(id: Int, puzzlersDatabaseRepository: PuzzlersDatabaseRepository, tokenDatabaseRepository: TokenDatabaseRepository, notificationsRepository: NotificationsRepository?, emailRepository: EmailRepository?) {
        val puzzler = puzzlersDatabaseRepository.getPuzzler(id)
        val changedPuzzler = puzzler.copy(dateTime = now)
        puzzlersDatabaseRepository.updatePuzzler(id, changedPuzzler, true)
        if (notificationsRepository != null) {
            val title = "New puzzler: " + puzzler.title
            val url = Config.baseUrl // TODO: To particular puzzler
            NotificationsUseCase.send(title, url, tokenDatabaseRepository, notificationsRepository, emailRepository)
        }
    }
}