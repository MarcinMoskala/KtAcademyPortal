package org.kotlinacademy.presentation.manager

import org.kotlinacademy.common.launchUI
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.ManagerRepository

class ManagerPresenter(val view: ManagerView, val secret: String) : BasePresenter() {

    private val repository by ManagerRepository.lazyGet()

    override fun onCreate() {
        view.loading = true
        refreshList()
    }

    private fun refreshList() {
        jobs += launchUI {
            try {
                val news = repository
                        .getPropositions(secret)
                        .run { infos + puzzlers }
                        .sortedByDescending { it.dateTime }
                view.showList(news)
            } catch (e: Throwable) {
                view.showError(e)
            } finally {
                view.loading = false
            }
        }
    }
}