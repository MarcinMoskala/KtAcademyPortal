package org.kotlinacademy.backend

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.pipeline.PipelineContext
import io.ktor.request.receiveOrNull
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import org.kotlinacademy.Endpoints
import org.kotlinacademy.backend.errors.MissingElementError
import org.kotlinacademy.backend.errors.MissingParameterError
import org.kotlinacademy.backend.errors.SecretInvalidError
import org.kotlinacademy.backend.repositories.db.ArticlesDatabaseRepository
import org.kotlinacademy.backend.repositories.db.InfoDatabaseRepository
import org.kotlinacademy.backend.repositories.db.PuzzlersDatabaseRepository
import org.kotlinacademy.backend.repositories.db.TokenDatabaseRepository
import org.kotlinacademy.backend.repositories.network.NotificationsRepository
import org.kotlinacademy.backend.usecases.FeedbackUseCese
import org.kotlinacademy.backend.usecases.NewsUseCase
import org.kotlinacademy.backend.usecases.NotificationsUseCase
import org.kotlinacademy.data.*

fun Routing.api() {
    val articlesDatabaseRepository by ArticlesDatabaseRepository.lazyGet()
    val infoDatabaseRepository by InfoDatabaseRepository.lazyGet()
    val puzzlersDatabaseRepository by PuzzlersDatabaseRepository.lazyGet()
    val tokenDatabaseRepository by TokenDatabaseRepository.lazyGet()
    val notificationRepository by NotificationsRepository.lazyGet()

    route(Endpoints.news) {
        get {
            val articles = articlesDatabaseRepository.getArticles()
            val infos = infoDatabaseRepository.getInfos().filter { it.accepted }
            val puzzlers = puzzlersDatabaseRepository.getPuzzlers().filter { it.accepted }
            call.respond(NewsData(articles, infos, puzzlers))
        }
    }

    route(Endpoints.info) {
        post(Endpoints.propose) {
            val info = receiveObject<InfoData>()
            NewsUseCase.propose(info)
            call.respond(HttpStatusCode.OK)
        }
        get("{id}/" + Endpoints.accept) {
            val id = requireParameter("id")
            NewsUseCase.acceptInfo(id)
            call.respond(HttpStatusCode.OK, "Success :)")
        }
        get("{id}/" + Endpoints.reject) {
            requireSecret()
            val id = requireParameter("id")
            infoDatabaseRepository.deleteInfo(id)
            call.respond(HttpStatusCode.OK, "Success :)")
        }
    }
    route(Endpoints.puzzler) {
        post(Endpoints.propose) {
            val puzzler = receiveObject<PuzzlerData>()
            NewsUseCase.propose(puzzler)
            call.respond(HttpStatusCode.OK)
        }
        get("{id}/" + Endpoints.accept) {
            val id = requireParameter("id")
            NewsUseCase.acceptPuzzler(id)
            call.respond(HttpStatusCode.OK, "Success :)")
        }
        get("{id}/" + Endpoints.reject) {
            requireSecret()
            val id = requireParameter("id")
            puzzlersDatabaseRepository.deletePuzzler(id)
            call.respond(HttpStatusCode.OK, "Success :)")
        }
    }

    route(Endpoints.feedback) {
        get {
            requireSecret()
            val newsList = FeedbackUseCese.getAll()
            call.respond(FeedbackData(newsList))
        }
        post {
            val feedback = receiveObject<Feedback>()
            FeedbackUseCese.add(feedback)
            call.respond(HttpStatusCode.OK)
        }
    }
    route(Endpoints.notification) {
        route(Endpoints.notificationRegister) {
            get {
                requireSecret()
                val tokens = tokenDatabaseRepository.getAllTokens()
                call.respond(tokens)
            }
            post {
                val registerTokenData = receiveObject<FirebaseTokenData>()
                tokenDatabaseRepository.addToken(registerTokenData.token, registerTokenData.type)
                call.respond(HttpStatusCode.OK)
            }
        }
        route(Endpoints.notificationSend) {
            post {
                requireSecret()
                val text = receiveObject<String>()
                notificationRepository ?: throw MissingElementError("Notification Repository")
                val url = "https://blog.kotlin-academy.com/"
                NotificationsUseCase.send(text, url)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}

private fun PipelineContext<Unit, ApplicationCall>.requireParameter(name: String) =
        call.parameters[name]?.toIntOrNull() ?: throw MissingParameterError(name)

// CHANGE: Now Secret-hash should be passed as query parameter instead of as a header
private fun PipelineContext<*, ApplicationCall>.requireSecret() {
    if (call.request.queryParameters["Secret-hash"] != Config.secretHash) throw SecretInvalidError()
}

private suspend inline fun <reified T : Any> PipelineContext<Unit, ApplicationCall>.receiveObject(): T {
    return call.receiveOrNull() ?: throw MissingParameterError(T::class.simpleName)
}