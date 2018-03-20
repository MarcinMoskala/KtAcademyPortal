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
import org.kotlinacademy.Endpoints
import org.kotlinacademy.backend.errors.MissingParameterError
import org.kotlinacademy.backend.errors.SecretInvalidError
import org.kotlinacademy.backend.usecases.FeedbackUseCese
import org.kotlinacademy.backend.usecases.NewsUseCase
import org.kotlinacademy.backend.usecases.NotificationsUseCase
import org.kotlinacademy.backend.usecases.TokenUseCase
import org.kotlinacademy.data.*

fun Routing.api() {

    route(Endpoints.news) {
        get {
            val newsData = NewsUseCase.getNewsData()
            call.respond(newsData)
        }
    }

    route(Endpoints.info) {
        post(Endpoints.propose) {
            val info = receiveObject<InfoData>()
            NewsUseCase.propose(info)
            call.respond(HttpStatusCode.OK)
        }
        get("{id}/" + Endpoints.accept) {
            val id = requireParameter("id")
            NewsUseCase.acceptInfo(id)
            call.respond(HttpStatusCode.OK, "Success :)")
        }
        get("{id}/" + Endpoints.reject) {
            requireSecret()
            val id = requireParameter("id")
            NewsUseCase.deleteInfo(id)
            call.respond(HttpStatusCode.OK, "Success :)")
        }
    }

    route(Endpoints.puzzler) {
        post(Endpoints.propose) {
            val puzzler = receiveObject<PuzzlerData>()
            NewsUseCase.propose(puzzler)
            call.respond(HttpStatusCode.OK)
        }
        get("{id}/" + Endpoints.accept) {
            val id = requireParameter("id")
            NewsUseCase.acceptPuzzler(id)
            call.respond(HttpStatusCode.OK, "Success :)")
        }
        get("{id}/" + Endpoints.reject) {
            requireSecret()
            val id = requireParameter("id")
            NewsUseCase.deletePuzzler(id)
            call.respond(HttpStatusCode.OK, "Success :)")
        }
    }

    route(Endpoints.feedback) {
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

    route(Endpoints.notification) {
        route(Endpoints.notificationRegister) {
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
        route(Endpoints.notificationSend) {
            post {
                requireSecret()
                val text = receiveObject<String>()
                NotificationsUseCase.send(text, Config.baseUrl)
                call.respond(HttpStatusCode.OK)
            }
        }
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