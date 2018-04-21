package org.kotlinacademy.backend

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.content.defaultResource
import io.ktor.content.resources
import io.ktor.content.static
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.gson.GsonConverter
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.util.error
import org.kotlinacademy.Headers
import org.kotlinacademy.backend.errors.MissingElementError
import org.kotlinacademy.backend.errors.MissingParameterError
import org.kotlinacademy.backend.errors.SecretInvalidError
import org.kotlinacademy.backend.repositories.db.Database
import org.kotlinacademy.gson

fun Application.main() {
    application = this
    log.info("Production: ${Config.production}")
    Database

    install(CallLogging)
    install(DefaultHeaders) {
        header(Headers.androidMobileMinVersion, "1.0.11")
    }
    install(StatusPages) {
        exception<MissingParameterError> { cause ->
            call.respond(HttpStatusCode.BadRequest, "Missing parameter ${cause.name}")
        }
        exception<MissingElementError> { cause ->
            environment.log.error(cause)
            call.respond(HttpStatusCode.InternalServerError, "Cannot process your request because of missing ${cause.name}")
        }
        exception<SecretInvalidError> {
            call.respond(HttpStatusCode.BadRequest, "This endpoint is protected and your secret is invalid")
        }
        exception<Throwable> { cause ->
            environment.log.error(cause)
            call.respond(HttpStatusCode.NotImplemented)
        }
    }

    install(ContentNegotiation) {
        register(ContentType.Application.Json, GsonConverter(gson))

    }

    install(Routing) {
        static {
            defaultResource("static/index.html")
            resources("static")
        }
        api()
    }

    launchSyncJobs()
}