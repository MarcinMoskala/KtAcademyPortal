package com.marcinmoskala.kotlinacademy.backend

import com.marcinmoskala.kotlinacademy.Endpoints.feedback
import com.marcinmoskala.kotlinacademy.Endpoints.news
import com.marcinmoskala.kotlinacademy.Endpoints.registerFirebaseToken
import com.marcinmoskala.kotlinacademy.Endpoints.tokenTypeAndroid
import com.marcinmoskala.kotlinacademy.Endpoints.tokenTypeWeb
import com.marcinmoskala.kotlinacademy.backend.repositories.db.DatabaseRepository
import com.marcinmoskala.kotlinacademy.backend.repositories.network.NotificationsRepository
import com.marcinmoskala.kotlinacademy.backend.usecases.*
import com.marcinmoskala.kotlinacademy.data.Feedback
import com.marcinmoskala.kotlinacademy.data.FeedbackData
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.data.NewsData
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

    route(news) {
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
    route(feedback) {
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
    route(registerFirebaseToken) {
        get(tokenTypeWeb) {
            requireSecret() ?: return@get
            val tokens = getTokens(TokenType.Web, databaseRepository)
            call.respond(tokens)
        }
        post(tokenTypeWeb) {
            val token = receiveObject<String>() ?: return@post
            addToken(token, TokenType.Web, databaseRepository, notificationRepository)
            call.respond(HttpStatusCode.OK)
        }
        get(tokenTypeAndroid) {
            requireSecret() ?: return@get
            val tokens = getTokens(TokenType.Android, databaseRepository)
            call.respond(tokens)
        }
        post(tokenTypeAndroid) {
            val token = receiveObject<String>() ?: return@post
            addToken(token, TokenType.Android, databaseRepository, notificationRepository)
            call.respond(HttpStatusCode.OK)
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