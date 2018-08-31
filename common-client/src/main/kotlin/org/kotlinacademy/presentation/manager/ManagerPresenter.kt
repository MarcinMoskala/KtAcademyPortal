package org.kotlinacademy.presentation.manager

import kotlinx.coroutines.experimental.launch
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.ManagerRepository
import kotlin.coroutines.experimental.CoroutineContext

class ManagerPresenter(
        private val uiContext: CoroutineContext,
        private val view: ManagerView,
        private val secret: String,
        private val repository: ManagerRepository
) : BasePresenter() {

    override fun onCreate() {
        view.loading = true
        showList()
    }

    fun acceptInfo(id: Int) {
        makeAction { repository.acceptInfo(id, secret) }
    }

    fun rejectInfo(id: Int) {
        makeAction { repository.rejectInfo(id, secret) }
    }

    fun acceptPuzzler(id: Int) {
        makeAction { repository.acceptPuzzler(id, secret) }
    }

    fun acceptImportantPuzzler(id: Int) {
        makeAction { repository.acceptImportantPuzzler(id, secret) }
    }

    fun puzzlerToTop(id: Int) {
        makeAction { repository.puzzlerToTop(id, secret) }
    }

    fun rejectPuzzler(id: Int) {
        makeAction { repository.rejectPuzzler(id, secret) }
    }

    fun acceptSnippet(id: Int) {
        makeAction { repository.acceptSnippet(id, secret) }
    }

    fun rejectSnippet(id: Int) {
        makeAction { repository.acceptSnippet(id, secret) }
    }

    private fun showList() {
        jobs += launch(uiContext) {
            try {
                val news = repository
                        .getPropositions(secret)
                        .run { infos + puzzlers + snippets }
                        .sortedBy { it.dateTime }
                view.showList(news)
            } catch (e: Throwable) {
                view.showError(e)
            } finally {
                view.loading = false
            }
        }
    }

    private fun makeAction(action: suspend () -> Unit) {
        launch(uiContext) {
            view.loading = true
            action()
            showList()
        }
    }
}