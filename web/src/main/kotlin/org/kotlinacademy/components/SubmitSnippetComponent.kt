package org.kotlinacademy.components

import kotlinx.coroutines.experimental.DefaultDispatcher
import org.kotlinacademy.common.getUrlParam
import org.kotlinacademy.common.secretInUrl
import org.kotlinacademy.data.*
import org.kotlinacademy.presentation.snippet.SnippetPresenter
import org.kotlinacademy.presentation.snippet.SnippetView
import org.kotlinacademy.respositories.*
import org.kotlinacademy.views.*
import react.RBuilder
import react.RProps
import react.dom.h3
import react.setState
import kotlin.properties.Delegates.observable

class SubmitSnippetComponent : BaseComponent<RProps, SubmitSnippetComponentState>(), SnippetView {

    private val presenter by presenter {
        SnippetPresenter(
                uiContext = DefaultDispatcher,
                view = this,
                id = getUrlParam("id")?.toIntOrNull(),
                secret = secretInUrl,
                newsRepository = NewsRepositoryImpl(),
                managerRepository = ManagerRepositoryImpl(),
                snippetRepository = SnippetRepositoryImpl()
        )
    }

    override var loading: Boolean by observable(false) { _, _, n ->
        setState { loading = n }
    }

    override var prefilled: Snippet? by observable(null as Snippet?) { _, _, n ->
        n ?: return@observable
        setState {
            title = n.title
            code = n.code
            explanation = n.explanation
            author = n.author
            authorUrl = n.authorUrl
        }
    }

    override fun RBuilder.render() {
        when {
            state.loading == true -> loadingView()
            state.showThankYouPage == true -> thankYouView()
            state.error != null -> errorView(state.error!!)
            else -> kaForm {
                h3 { +"Share your puzzler :D" }

                textFieldView("Title", value = state.title, lines = 1) {
                    setState { title = it }
                }
                textFieldView("Code", value = state.code) {
                    setState { code = it }
                }
                textFieldView("Explanation", value = state.explanation) {
                    setState { explanation = it }
                }
                textFieldView("Your name", value = state.author, lines = 1) {
                    setState { author = it }
                }
                textFieldView("Your url", value = state.authorUrl, lines = 1) {
                    setState { authorUrl = it }
                }

                submitButton("Submit", onClick = fun() {
                    val snippetData = SnippetData(
                            title = state.title,
                            code = state.code ?: "",
                            explanation = state.explanation,
                            author = state.author,
                            authorUrl = state.authorUrl
                    )
                    presenter.onSubmitClicked(snippetData)
                })
            }
        }
    }

    override fun backToNewsAndShowSuccess() {
        setState { showThankYouPage = true }
        backAfterDelay(millis = 3_000)
    }
}

interface SubmitSnippetComponentState : BaseState {
    var title: String?
    var code: String?
    var explanation: String?
    var author: String?
    var authorUrl: String?
    var loading: Boolean?
    var showThankYouPage: Boolean?
}