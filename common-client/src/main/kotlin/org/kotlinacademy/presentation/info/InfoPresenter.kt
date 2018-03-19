package org.kotlinacademy.presentation.info

import org.kotlinacademy.common.launchUI
import org.kotlinacademy.data.Info
import org.kotlinacademy.data.InfoData
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.InfoRepository

class InfoPresenter(private val view: InfoView) : BasePresenter() {

    private val infoRepository by InfoRepository.lazyGet()

    fun onSubmitCommentClicked(info: InfoData) {
        view.loading = true
        jobs += launchUI {
            try {
                infoRepository.propose(info)
                view.backToNewsAndShowSuccess()
            } catch (e: Throwable) {
                view.showError(e)
            } finally {
                view.loading = false
            }
        }
    }
}