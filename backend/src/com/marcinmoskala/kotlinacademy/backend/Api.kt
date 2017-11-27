package com.marcinmoskala.kotlinacademy.backend

import com.marcinmoskala.kotlinacademy.Endpoints.feedback
import com.marcinmoskala.kotlinacademy.Endpoints.news
import com.marcinmoskala.kotlinacademy.Endpoints.registerFirebaseWebToken
import com.marcinmoskala.kotlinacademy.backend.db.Database
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

fun Routing.api(database: Database) {
    apiNews(database)
}

/*
GET /news
returns NewsData

PUT /news
Body: News
returns 200
 */
fun Routing.apiNews(database: Database) {
    route(news) {
        get {
            val newsList = database.getNews()
            call.respond(NewsData(newsList))
        }
        put {
            requireSecret() ?: return@put
            val news = receiveObject<News>() ?: return@put
            database.updateOrAdd(news)
            call.respond(HttpStatusCode.OK)
        }
    }
    route(feedback) {
        get {
            requireSecret() ?: return@get
            val newsList = database.getComments()
            call.respond(FeedbackData(newsList))
        }
        post {
            val feedback = receiveObject<Feedback>() ?: return@post
            database.add(feedback)
            call.respond(HttpStatusCode.OK)
        }
    }
    route(registerFirebaseWebToken) {
        get {
            requireSecret() ?: return@get
            call.respond(database.getWebTokens())
        }
        post {
            val token = receiveObject<String>() ?: return@post
            database.addWebToken(token)
            call.respond(HttpStatusCode.OK)
        }
    }
}

private suspend fun PipelineContext<*, ApplicationCall>.requireSecret(): Unit? {
    if (call.request.headers["Secret-hash"] != secretHash) {
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