package org.kotlinacademy.backend

import org.kotlinacademy.Endpoints
import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.backend.repositories.network.NotificationsRepository
import org.kotlinacademy.backend.usecases.*
import org.kotlinacademy.data.*
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.pipeline.PipelineContext
import io.ktor.request.receiveOrNull
import io.ktor.response.respond
import io.ktor.routing.*

fun Routing.api() {
    val databaseRepository by DatabaseRepository.lazyGet()
    val notificationRepository by NotificationsRepository.lazyGet()

    route(Endpoints.news) {
        get {
            val newsList = getAllNews(databaseRepository)
            call.respond(NewsData(newsList))
        }
        put {
            requireSecret() ?: return@put
            val news = receiveObject<News>() ?: return@put
            addOrUpdateNews(news, databaseRepository, notificationRepository)
            call.respond(HttpStatusCode.OK)
        }
    }
    route(Endpoints.feedback) {
        get {
            requireSecret() ?: return@get
            val newsList = getAllFeedback(databaseRepository)
            call.respond(FeedbackData(newsList))
        }
        post {
            val feedback = receiveObject<Feedback>() ?: return@post
            addFeedback(feedback, databaseRepository)
            call.respond(HttpStatusCode.OK)
        }
    }
    route(Endpoints.notification) {
        route(Endpoints.notificationRegister) {
            get {
                requireSecret() ?: return@get
                val tokens = getTokenData(databaseRepository)
                call.respond(tokens)
            }
            post {
                val registerTokenData = receiveObject<FirebaseTokenData>() ?: return@post
                addToken(registerTokenData, databaseRepository)
                call.respond(HttpStatusCode.OK)
            }
        }
        route(Endpoints.notificationSend) {
            post {
                requireSecret() ?: return@post
                val text = receiveObject<String>() ?: return@post
                if (notificationRepository == null) {
                    call.respond(HttpStatusCode.ServiceUnavailable, "No notification repository!")
                    return@post
                }
                sendNotifications(text, databaseRepository, notificationRepository)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}

private suspend fun PipelineContext<*, ApplicationCall>.requireSecret(): Unit? {
    if (call.request.headers["Secret-hash"] != Config.secretHash) {
        call.respond("You need to provide hash of admin secret for this reqeust")
        return null
    }
    return Unit
}

private suspend inline fun <reified T : Any> PipelineContext<Unit, ApplicationCall>.receiveObject(): T? {
    val data = call.receiveOrNull<T>()
    if (data == null) {
        call.respond(HttpStatusCode.BadRequest, "Invalid body. Should be ${T::class.simpleName} as JSON.")
        return null
    } else {
        return data
    }
}