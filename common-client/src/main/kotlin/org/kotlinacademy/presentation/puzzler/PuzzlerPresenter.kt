package org.kotlinacademy.presentation.puzzler

import org.kotlinacademy.common.launchUI
import org.kotlinacademy.data.PuzzlerData
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.ManagerRepository
import org.kotlinacademy.respositories.NewsRepository
import org.kotlinacademy.respositories.PuzzlerRepository

class PuzzlerPresenter(
        private val view: PuzzlerView,
        private val id: Int?,
        private val secret: String?,
        private val newsRepository: NewsRepository,
        private val managerRepository: ManagerRepository,
        private val puzzlerRepository: PuzzlerRepository
) : BasePresenter() {

    override fun onCreate() {
        if (id != null && secret != null) {
            view.loading = true
            jobs += launchUI {
                try {
                    val publishedPuzzlers = newsRepository.getNewsData().puzzlers
                    val unpublishedPuzzlers = managerRepository.getPropositions(secret).puzzlers
                    val puzzler = (publishedPuzzlers + unpublishedPuzzlers).firstOrNull { it.id == id }
                    view.prefilled = puzzler
                } catch (e: Throwable) {
                    view.showError(e)
                } finally {
                    view.loading = false
                }
            }
        }
    }

    fun onSubmitClicked(puzzlerData: PuzzlerData) {
        view.loading = true
        jobs += launchUI {
            try {
                val prefilled = view.prefilled
                if (prefilled != null && secret != null) {
                    val newPuzzler = prefilled.copy(data = puzzlerData)
                    puzzlerRepository.update(newPuzzler, secret)
                } else {
                    puzzlerRepository.propose(puzzlerData)
                }
                view.backToNewsAndShowSuccess()
            } catch (e: Throwable) {
                view.showError(e)
            } finally {
                view.loading = false
            }
        }
    }
}