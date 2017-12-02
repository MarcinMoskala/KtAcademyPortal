package com.marcinmoskala.kotlinacademy.backend

import com.marcinmoskala.kotlinacademy.Endpoints.feedback
import com.marcinmoskala.kotlinacademy.Endpoints.news
import com.marcinmoskala.kotlinacademy.Endpoints.registerFirebaseWebToken
import com.marcinmoskala.kotlinacademy.backend.api.NotificationsRepository
import com.marcinmoskala.kotlinacademy.backend.db.DatabaseRepository
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
            val newsList = databaseRepository.getNews()
            call.respond(NewsData(newsList))
        }
        put {
            requireSecret() ?: return@put
            val news = receiveObject<News>() ?: return@put
            databaseRepository.addOrReplaceNews(news)
            call.respond(HttpStatusCode.OK)
        }
    }
    route(feedback) {
        get {
            requireSecret() ?: return@get
            val newsList = databaseRepository.getFeedback()
            call.respond(FeedbackData(newsList))
        }
        post {
            val feedback = receiveObject<Feedback>() ?: return@post
            databaseRepository.addFeedback(feedback)
            call.respond(HttpStatusCode.OK)
        }
    }
    route(registerFirebaseWebToken) {
        get {
            requireSecret() ?: return@get
            call.respond(databaseRepository.getWebTokens())
        }
        post {
            val token = receiveObject<String>() ?: return@post
            databaseRepository.addWebToken(token)
            notificationRepository?.sendNotification("Thank you for registration :)", token)
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