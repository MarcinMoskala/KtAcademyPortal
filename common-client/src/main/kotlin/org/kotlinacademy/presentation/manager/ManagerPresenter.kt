package org.kotlinacademy.presentation.manager

import org.kotlinacademy.common.launchUI
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.ManagerRepository

class ManagerPresenter(
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

    private fun showList() {
        jobs += launchUI {
            try {
                val news = repository
                        .getPropositions(secret)
                        .run { infos + puzzlers }
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
        launchUI {
            view.loading = true
            action()
            showList()
        }
    }
}