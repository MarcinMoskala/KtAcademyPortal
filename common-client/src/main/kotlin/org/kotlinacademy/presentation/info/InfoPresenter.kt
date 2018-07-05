package org.kotlinacademy.presentation.info

import org.kotlinacademy.common.launchUI
import org.kotlinacademy.data.InfoData
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.InfoRepository
import org.kotlinacademy.respositories.ManagerRepository
import org.kotlinacademy.respositories.NewsRepository

class InfoPresenter(
        private val view: InfoView,
        private val id: Int?,
        private val secret: String?,
        private val newsRepository: NewsRepository,
        private val managerRepository: ManagerRepository,
        private val infoRepository: InfoRepository
) : BasePresenter() {

    override fun onCreate() {
        if (id != null && secret != null) {
            view.loading = true
            jobs += launchUI {
                try {
                    val publishedInfos = newsRepository.getNewsData().infos
                    val unpublishedInfos = managerRepository.getPropositions(secret).infos
                    val info = (publishedInfos + unpublishedInfos).firstOrNull { it.id == id }
                    view.prefilled = info
                } catch (e: Throwable) {
                    view.showError(e)
                } finally {
                    view.loading = false
                }
            }
        }
    }

    fun onSubmitClicked(infoData: InfoData) {
        view.loading = true
        jobs += launchUI {
            try {
                val prefilled = view.prefilled
                if (prefilled != null && secret != null) {
                    val newInfo = prefilled.copy(data = infoData)
                    infoRepository.update(newInfo, secret)
                } else {
                    infoRepository.propose(infoData)
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