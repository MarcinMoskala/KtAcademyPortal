package org.kotlinacademy.backend

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.pipeline.PipelineContext
import io.ktor.request.receiveOrNull
import io.ktor.response.respond
import io.ktor.routing.*
import org.kotlinacademy.Endpoints
import org.kotlinacademy.backend.errors.MissingElementError
import org.kotlinacademy.backend.errors.MissingParameterError
import org.kotlinacademy.backend.errors.SecretInvalidError
import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.repositories.network.NotificationsRepository
import org.kotlinacademy.backend.usecases.*
import org.kotlinacademy.data.*

fun Routing.api() {
    val databaseRepository by DatabaseRepository.lazyGet()
    val emailRepository by EmailRepository.lazyGet()
    val notificationRepository by NotificationsRepository.lazyGet()

    route(Endpoints.news) {
        get {
            val newsList = getAllNews(databaseRepository)
            call.respond(NewsData(newsList))
        }
        put {
            requireSecret()
            val news = receiveObject<News>()
            addOrUpdateNews(news, databaseRepository, notificationRepository, emailRepository)
            call.respond(HttpStatusCode.OK)
        }
        delete("{id}") {
            requireSecret()
            val newsId = call.parameters["id"]?.toIntOrNull() ?: throw MissingParameterError("id")
            deleteNews(newsId, databaseRepository)
        }
    }
    route(Endpoints.feedback) {
        get {
            requireSecret()
            val newsList = getAllFeedback(databaseRepository)
            call.respond(FeedbackData(newsList))
        }
        post {
            val feedback = receiveObject<Feedback>()
            addFeedback(feedback, emailRepository, databaseRepository)
            call.respond(HttpStatusCode.OK)
        }
    }
    route(Endpoints.notification) {
        route(Endpoints.notificationRegister) {
            get {
                requireSecret()
                val tokens = databaseRepository.getAllTokens()
                call.respond(tokens)
            }
            post {
                val registerTokenData = receiveObject<FirebaseTokenData>()
                databaseRepository.addToken(registerTokenData.token, registerTokenData.type)
                call.respond(HttpStatusCode.OK)
            }
        }
        route(Endpoints.notificationSend) {
            post {
                requireSecret()
                val text = receiveObject<String>()
                notificationRepository ?: throw MissingElementError("Notification Repository")
                val url = "https://blog.kotlin-academy.com/"
                sendNotifications(text, url, databaseRepository, notificationRepository, emailRepository)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}

private fun PipelineContext<*, ApplicationCall>.requireSecret() {
    if (call.request.headers["Secret-hash"] != Config.secretHash) throw SecretInvalidError()
}

private suspend inline fun <reified T : Any> PipelineContext<Unit, ApplicationCall>.receiveObject(): T {
    return call.receiveOrNull() ?: throw MissingParameterError(T::class.simpleName)
}