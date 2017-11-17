package com.marcinmoskala.kotlinacademy.backend

import com.google.gson.GsonBuilder
import com.marcinmoskala.kotlinacademy.gson
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.content.default
import io.ktor.content.files
import io.ktor.content.static
import io.ktor.features.*
import io.ktor.gson.GsonConverter
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.util.error
import io.ktor.websocket.WebSockets

fun Application.main() {
    val config = environment.config.config("service")
    val mode = config.property("environment").getString()
    log.info("Environment: $mode")
    val production = mode == "production"

    if (!production) {
        install(CallLogging)
    }

    install(StatusPages) {
        exception<Throwable> { cause ->
            environment.log.error(cause)
            call.respond(HttpStatusCode.NotImplemented)
        }
    }

    install(ContentNegotiation) {
        register(ContentType.Application.Json, GsonConverter(gson))
    }

    val database = Database(this)
    install(Routing) {
        static {
            default("static/index.html")
            files("static")
        }
        api(database)
    }
}