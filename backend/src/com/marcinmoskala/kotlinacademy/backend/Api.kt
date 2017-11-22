package com.marcinmoskala.kotlinacademy.backend

import com.marcinmoskala.kotlinacademy.Endpoints
import com.marcinmoskala.kotlinacademy.backend.db.Database
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.data.NewsData
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.pipeline.PipelineContext
import io.ktor.request.receiveOrNull
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.put
import io.ktor.routing.route
import kotlinx.coroutines.experimental.time.delay
import java.time.Duration

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
    route(Endpoints.news) {
        get {
            val newsList = database.getNews()
            call.respond(NewsData(newsList))
        }
        put {
            receiveObject<News> { newsData ->
                database.updateOrAdd(newsData)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}

private suspend inline fun <reified T : Any> PipelineContext<Unit, ApplicationCall>.receiveObject(callback: (T) -> Unit) {
    val data = call.receiveOrNull<T>()
    if (data == null) {
        call.respond(HttpStatusCode.BadRequest, "Invalid body. Should be ${T::class.simpleName} as JSON.")
    } else {
        callback(data)
    }
}