package com.marcinmoskala.kotlinacademy.backend

import com.marcinmoskala.kotlinacademy.Endpoints
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import javax.xml.ws.Endpoint

fun Routing.api(database: Database) {
    apiNews(database)
}

/*
GET /news
 */
fun Routing.apiNews(database: Database) {
    get(Endpoints.getNews) {
        call.respond(database.getNews())
    }
}