package org.kotlinacademy.components

import kotlinx.coroutines.experimental.DefaultDispatcher
import org.kotlinacademy.common.getUrlParam
import org.kotlinacademy.common.secretInUrl
import org.kotlinacademy.data.*
import org.kotlinacademy.presentation.puzzler.PuzzlerPresenter
import org.kotlinacademy.presentation.puzzler.PuzzlerView
import org.kotlinacademy.respositories.ManagerRepositoryImpl
import org.kotlinacademy.respositories.NewsRepositoryImpl
import org.kotlinacademy.respositories.PuzzlerRepositoryImpl
import org.kotlinacademy.views.*
import react.RBuilder
import react.RProps
import react.ReactElement
import react.dom.h3
import react.setState
import kotlin.properties.Delegates.observable

class SubmitPuzzlerComponent : BaseComponent<RProps, SubmitPuzzlerComponentState>(), PuzzlerView {

    private val presenter by presenter {
        PuzzlerPresenter(
                uiContext = DefaultDispatcher,
                view = this,
                id = getUrlParam("id")?.toIntOrNull(),
                secret = secretInUrl,
                newsRepository = NewsRepositoryImpl(),
                managerRepository = ManagerRepositoryImpl(),
                puzzlerRepository = PuzzlerRepositoryImpl()
        )
    }

    override var loading: Boolean by observable(false) { _, _, n ->
        setState { loading = n }
    }

    override fun SubmitPuzzlerComponentState.init() {
        actualQuestion = "What does it display? Some possibilities:"
    }

    override var prefilled: Puzzler? by observable(null as Puzzler?) { _, _, n ->
        n ?: return@observable
        setState {
            title = n.title
            level = n.level
            codeQuestion = n.codeQuestion
            actualQuestion = n.actualQuestion
            answers = n.answers
            correctAnswer = n.correctAnswer
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
                val levelSelect = selectFieldView("Level", possibilities = listOf("Beginner", "Advanced", "Expert"), initial = state.level)

                textFieldView("Code question", value = state.codeQuestion) {
                    setState { codeQuestion = it }
                }
                textFieldView("Actual question", value = state.actualQuestion, lines = 1) {
                    setState { actualQuestion = it }
                }
                textFieldView("Give some possible answers", value = state.answers) {
                    setState { answers = it }
                }
                textFieldView("Correct answer", value = state.correctAnswer, lines = 1) {
                    setState { correctAnswer = it }
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
                    console.log("Zaczynam! \"SubmitPuzzlerComponentState(title=${state.title}, level=${levelSelect.value}, codeQuestion=${state.codeQuestion}, actualQuestion=${state.actualQuestion}, answers=${state.answers}, correctAnswer=${state.correctAnswer}, explanation=${state.explanation}, author=${state.author}, authorUrl=${state.authorUrl}, loading=${state.loading}, showThankYouPage=${state.showThankYouPage})\"")
                    val info = PuzzlerData(
                            title = state.title ?: return,
                            level = levelSelect.value,
                            actualQuestion = state.actualQuestion ?: return,
                            codeQuestion = state.codeQuestion ?: return,
                            answers = state.answers ?: return,
                            correctAnswer = state.correctAnswer ?: return,
                            explanation = state.explanation ?: "",
                            author = state.author,
                            authorUrl = state.authorUrl
                    )
                    console.log("Info to $info")
                    presenter.onSubmitClicked(info)
                })
            }
        }
    }

    override fun backToNewsAndShowSuccess() {
        setState { showThankYouPage = true }
        backAfterDelay(millis = 3_000)
    }
}

interface SubmitPuzzlerComponentState : BaseState {
    var title: String?
    var level: String?
    var codeQuestion: String?
    var actualQuestion: String?
    var answers: String?
    var correctAnswer: String?
    var explanation: String?
    var author: String?
    var authorUrl: String?
    var loading: Boolean?
    var showThankYouPage: Boolean?
}