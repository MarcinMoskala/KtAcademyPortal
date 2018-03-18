package org.kotlinacademy.presentation.puzzler

import org.kotlinacademy.common.launchUI
import org.kotlinacademy.data.Puzzler
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.PuzzlerRepository

class PuzzlerPresenter(private val view: PuzzlerView) : BasePresenter() {

    private val puzzlersRepo by PuzzlerRepository.lazyGet()

    fun onSubmitCommentClicked(puzzler: Puzzler) {
        view.loading = true
        jobs += launchUI {
            try {
                puzzlersRepo.propose(puzzler)
                view.backToNewsAndShowSuccess()
            } catch (e: Throwable) {
                view.showError(e)
            } finally {
                view.loading = false
            }
        }
    }
}