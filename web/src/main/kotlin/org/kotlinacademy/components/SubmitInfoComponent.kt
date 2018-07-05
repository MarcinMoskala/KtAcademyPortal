package org.kotlinacademy.components

import kotlinx.html.FORM
import kotlinx.html.id
import org.kotlinacademy.common.getUrlParam
import org.kotlinacademy.common.secretInUrl
import org.kotlinacademy.data.*
import org.kotlinacademy.presentation.info.InfoPresenter
import org.kotlinacademy.presentation.info.InfoView
import org.kotlinacademy.respositories.InfoRepositoryImpl
import org.kotlinacademy.respositories.ManagerRepositoryImpl
import org.kotlinacademy.respositories.NewsRepositoryImpl
import org.kotlinacademy.views.*
import react.RBuilder
import react.RProps
import react.ReactElement
import react.dom.RDOMBuilder
import react.dom.div
import react.dom.h3
import react.dom.img
import kotlin.properties.Delegates.observable

class SubmitInfoComponent : BaseComponent<RProps, SubmitInfoComponentState>(), InfoView {

    private val presenter by presenter {
        InfoPresenter(this,
                id = getUrlParam("id")?.toIntOrNull(),
                secret = secretInUrl,
                newsRepository = NewsRepositoryImpl(),
                managerRepository = ManagerRepositoryImpl(),
                infoRepository = InfoRepositoryImpl()
        )
    }

    override var loading: Boolean by observable(false) { _, _, n ->
        setState { state.loading = n }
    }
    override var prefilled: Info? by observable(null as Info?) { _, _, n ->
        n ?: return@observable
        setState {
            title = n.title
            imageUrl = n.imageUrl
            description = n.description
            sources = n.sources
            url = n.url
            author = n.author
            authorUrl = n.authorUrl
        }
    }

    override fun RBuilder.render(): ReactElement? = when {
        state.loading == true -> loadingView()
        state.showThankYouPage == true -> thankYouView()
        state.error != null -> errorView(state.error!!)
        else -> kaForm {
            val imageContainerId: String = randomId()
            val imageId: String = randomId()
            h3 { +"Share important news from last weeks" }

            textFieldView("Title of the news", value = state.title, lines = 1) {
                setState { title = it }
            }

            hiddenImageContainter(initial = state.imageUrl, containerId = imageContainerId, imageId = imageId)
            textFieldView("Url to image", value = state.imageUrl, lines = 1, onChange = { imageUrl ->
                val container = getById(imageContainerId) ?: return@textFieldView
                val element = getById(imageId) ?: return@textFieldView
                if (imageUrl == null) {
                    container.hide()
                } else {
                    container.show()
                    element.src = imageUrl
                }
                setState { this.imageUrl = imageUrl }
            })
            textFieldView("Here you can describe the news", value = state.description) {
                setState { description = it }
            }
            textFieldView("Give some sources for us and readers", value = state.sources) {
                setState { sources = it }
            }
            textFieldView("Does this news refer to some URL? If so, leave it here.", value = state.url, lines = 1) {
                setState { url = it }
            }
            textFieldView("Your name", value = state.author, lines = 1) {
                setState { author = it }
            }
            textFieldView("Your url", value = state.authorUrl, lines = 1) {
                setState { authorUrl = it }
            }

            submitButton("Submit", onClick = fun() {
                val info = InfoData(
                        title = state.title ?: return,
                        imageUrl = state.imageUrl ?: return,
                        description = state.description ?: return,
                        sources = state.sources ?: "",
                        url = state.url,
                        author = state.author,
                        authorUrl = state.authorUrl
                )
                presenter.onSubmitClicked(info)
            })
        }
    }

    override fun backToNewsAndShowSuccess() {
        setState { showThankYouPage = true }
        backAfterDelay(millis = 3_000)
    }

    private fun RDOMBuilder<FORM>.hiddenImageContainter(initial: String? = null, containerId: String, imageId: String) {
        div(classes = "center-text hidden") {
            attrs { this.id = containerId }
            img(classes = "article-image", src = initial) {
                attrs { this.id = imageId }
            }
        }
    }
}

external interface SubmitInfoComponentState : BaseState {
    var title: String?
    var imageUrl: String?
    var description: String?
    var sources: String?
    var url: String?
    var author: String?
    var authorUrl: String?

    var loading: Boolean?
    var showThankYouPage: Boolean?
}