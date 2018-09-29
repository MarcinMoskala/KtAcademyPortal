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
import org.kotlinacademy.Endpoints.accept
import org.kotlinacademy.Endpoints.acceptImportant
import org.kotlinacademy.Endpoints.article
import org.kotlinacademy.Endpoints.atom
import org.kotlinacademy.Endpoints.delete
import org.kotlinacademy.Endpoints.feedback
import org.kotlinacademy.Endpoints.info
import org.kotlinacademy.Endpoints.log
import org.kotlinacademy.Endpoints.moveTop
import org.kotlinacademy.Endpoints.news
import org.kotlinacademy.Endpoints.notification
import org.kotlinacademy.Endpoints.notificationRegister
import org.kotlinacademy.Endpoints.notificationSend
import org.kotlinacademy.Endpoints.propose
import org.kotlinacademy.Endpoints.propositions
import org.kotlinacademy.Endpoints.puzzler
import org.kotlinacademy.Endpoints.reject
import org.kotlinacademy.Endpoints.rss
import org.kotlinacademy.Endpoints.snippet
import org.kotlinacademy.Endpoints.unpublish
import org.kotlinacademy.backend.errors.MissingParameterError
import org.kotlinacademy.backend.errors.SecretInvalidError
import org.kotlinacademy.backend.usecases.*
import org.kotlinacademy.data.*

fun Routing.api() {

    route(news) {
        get {
            val newsData = NewsUseCase.getAcceptedNewsData()
            call.respond(newsData)
        }
        get(propositions) {
            requireSecret()
            val newsData = NewsUseCase.getPropositions()
            call.respond(newsData)
        }
    }

    route(info) {
        post(propose) {
            val info = receiveObject<InfoData>()
            NewsUseCase.propose(info)
            call.respond(HttpStatusCode.OK)
        }
        post {
            requireSecret()
            val info = receiveObject<Info>()
            NewsUseCase.update(info)
            call.respond(HttpStatusCode.OK)
        }
        post("{id}/$accept") {
            requireSecret()
            val id = requireParameter("id")
            NewsUseCase.acceptInfo(id)
            call.respond(HttpStatusCode.OK)
        }
        post("{id}/$reject") {
            requireSecret()
            val id = requireParameter("id")
            NewsUseCase.deleteInfo(id)
            call.respond(HttpStatusCode.OK)
        }
    }

    route(snippet) {
        post(propose) {
            val info = receiveObject<SnippetData>()
            NewsUseCase.propose(info)
            call.respond(HttpStatusCode.OK)
        }
        post {
            requireSecret()
            val info = receiveObject<Snippet>()
            NewsUseCase.update(info)
            call.respond(HttpStatusCode.OK)
        }
        post("{id}/$accept") {
            requireSecret()
            val id = requireParameter("id")
            NewsUseCase.acceptSnippet(id)
            call.respond(HttpStatusCode.OK)
        }
        post("{id}/$reject") {
            requireSecret()
            val id = requireParameter("id")
            NewsUseCase.deleteSnippet(id)
            call.respond(HttpStatusCode.OK)
        }
    }

    route(puzzler) {
        post(propose) {
            val puzzler = receiveObject<PuzzlerData>()
            NewsUseCase.propose(puzzler)
            call.respond(HttpStatusCode.OK)
        }
        post {
            requireSecret()
            val puzzler = receiveObject<Puzzler>()
            NewsUseCase.update(puzzler)
            call.respond(HttpStatusCode.OK)
        }
        post("{id}/$accept") {
            requireSecret()
            val id = requireParameter("id")
            NewsUseCase.acceptPuzzler(id)
            call.respond(HttpStatusCode.OK)
        }
        post("{id}/$acceptImportant") {
            requireSecret()
            val id = requireParameter("id")
            NewsUseCase.acceptImportantPuzzler(id)
            call.respond(HttpStatusCode.OK)
        }
        post("{id}/$moveTop") {
            requireSecret()
            val id = requireParameter("id")
            NewsUseCase.movePuzzlerTop(id)
            call.respond(HttpStatusCode.OK)
        }
        post("{id}/$reject") {
            requireSecret()
            val id = requireParameter("id")
            NewsUseCase.deletePuzzler(id)
            call.respond(HttpStatusCode.OK)
        }
        get("{id}/$unpublish") { // HTTP GET, to allow using by link
            requireSecret()
            val id = requireParameter("id")
            NewsUseCase.unpublishPuzzler(id)
            call.respond(HttpStatusCode.OK)
        }
    }

    route(article) {
        get("{id}/$delete") { // Get, to use as a link
            requireSecret()
            val id = requireParameter("id")
            NewsUseCase.deleteArticle(id)
            call.respond(HttpStatusCode.OK)
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
                NotificationsUseCase.sendToAll(text, Config.baseUrl)
                call.respond(HttpStatusCode.OK)
            }
        }
    }

    route(log) {
        post {
            val data = receiveObject<Map<String, String>>()
            val deviceType = data["deviceType"] ?: ""
            val userId = data["publicationId"] ?: throw MissingParameterError("publicationId")
            val action = data["action"] ?: throw MissingParameterError("action")
            val extra = data["extra"] ?: ""
            LogUseCase.add(deviceType, userId, action, extra)
            call.respond(HttpStatusCode.OK)
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

private fun PipelineContext<*, ApplicationCall>.requireSecret() {
    if (!correctSecret()) throw SecretInvalidError()
}

private fun PipelineContext<*, ApplicationCall>.correctSecret() =
        call.request.queryParameters[Endpoints.apiSecretKey] == Config.secretHash

private suspend inline fun <reified T : Any> PipelineContext<Unit, ApplicationCall>.receiveObject(): T {
    return call.receiveOrNull() ?: throw MissingParameterError(T::class.simpleName)
}