package com.marcinmoskala.kotlinacademy.backend

import com.marcinmoskala.kotlinacademy.Endpoints
import com.marcinmoskala.kotlinacademy.data.News
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveOrNull
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.put
import javax.xml.ws.Endpoint

fun Routing.api(database: Database) {
    apiNews(database)
}

/*
GET /news
returns List<News>

PUT /news
Body: News
returns 200
 */
fun Routing.apiNews(database: Database) {
    get(Endpoints.getNews) {
        val newsList = database.getNews()
        call.respond(newsList)
    }
    put(Endpoints.getNews) {
        val news = call.receiveOrNull<News>()
        if(news == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid body. Should be News as JSON.")
        } else {
            database.updateOrAdd(news)
            call.respond(HttpStatusCode.OK)
        }
    }
}