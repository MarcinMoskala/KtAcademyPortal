package org.kotlinacademy.backend

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.pipeline.PipelineContext
import io.ktor.request.receiveOrNull
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import org.kotlinacademy.Endpoints.accept
import org.kotlinacademy.Endpoints.atom
import org.kotlinacademy.Endpoints.feedback
import org.kotlinacademy.Endpoints.info
import org.kotlinacademy.Endpoints.news
import org.kotlinacademy.Endpoints.notification
import org.kotlinacademy.Endpoints.notificationRegister
import org.kotlinacademy.Endpoints.notificationSend
import org.kotlinacademy.Endpoints.propose
import org.kotlinacademy.Endpoints.puzzler
import org.kotlinacademy.Endpoints.reject
import org.kotlinacademy.Endpoints.rss
import org.kotlinacademy.backend.errors.MissingParameterError
import org.kotlinacademy.backend.errors.SecretInvalidError
import org.kotlinacademy.backend.usecases.*
import org.kotlinacademy.data.*

fun Routing.api() {

    route(news) {
        get {
            val newsData = NewsUseCase.getNewsData()
            call.respond(newsData)
        }
    }

    route(info) {
        post(propose) {
            val info = receiveObject<InfoData>()
            NewsUseCase.propose(info)
            call.respond(HttpStatusCode.OK)
        }
        get("{id}/$accept") {
            val id = requireParameter("id")
            NewsUseCase.acceptInfo(id)
            call.respond(HttpStatusCode.OK, "Success :)")
        }
        get("{id}/$reject") {
            requireSecret()
            val id = requireParameter("id")
            NewsUseCase.deleteInfo(id)
            call.respond(HttpStatusCode.OK, "Success :)")
        }
    }

    route(puzzler) {
        post(propose) {
            val puzzler = receiveObject<PuzzlerData>()
            NewsUseCase.propose(puzzler)
            call.respond(HttpStatusCode.OK)
        }
        get("{id}/$accept") {
            val id = requireParameter("id")
            NewsUseCase.acceptPuzzler(id)
            call.respond(HttpStatusCode.OK, "Success :)")
        }
        get("{id}/$reject") {
            requireSecret()
            val id = requireParameter("id")
            NewsUseCase.deletePuzzler(id)
            call.respond(HttpStatusCode.OK, "Success :)")
        }
    }

    route(feedback) {
        get {
            requireSecret()
            val newsList = FeedbackUseCese.getAll()
            call.respond(FeedbackData(newsList))
        }
        post {
            val feedback = receiveObject<Feedback>()
            FeedbackUseCese.add(feedback)
            call.respond(HttpStatusCode.OK)
        }
    }

    route(notification) {
        route(notificationRegister) {
            get {
                requireSecret()
                val tokens = TokenUseCase.getAll()
                call.respond(tokens)
            }
            post {
                val registerTokenData = receiveObject<FirebaseTokenData>()
                TokenUseCase.register(registerTokenData)
                call.respond(HttpStatusCode.OK)
            }
        }
        route(notificationSend) {
            post {
                requireSecret()
                val text = receiveObject<String>()
                NotificationsUseCase.send(text, Config.baseUrl)
                call.respond(HttpStatusCode.OK)
            }
        }
    }

    get(rss) {
        val feed = RssUseCase.getRssFeed()
        call.respond(feed)
    }
    get(atom) {
        val feed = RssUseCase.getAtomFeed()
        call.respond(feed)
    }
}

private fun PipelineContext<Unit, ApplicationCall>.requireParameter(name: String) =
        call.parameters[name]?.toIntOrNull() ?: throw MissingParameterError(name)

// CHANGE: Now Secret-hash should be passed as query parameter instead of as a header
private fun PipelineContext<*, ApplicationCall>.requireSecret() {
    if (call.request.queryParameters["Secret-hash"] != Config.secretHash) throw SecretInvalidError()
}

private suspend inline fun <reified T : Any> PipelineContext<Unit, ApplicationCall>.receiveObject(): T {
    return call.receiveOrNull() ?: throw MissingParameterError(T::class.simpleName)
}