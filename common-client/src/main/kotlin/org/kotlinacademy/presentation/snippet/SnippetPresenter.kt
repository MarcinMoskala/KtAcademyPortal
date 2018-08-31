package org.kotlinacademy.presentation.snippet

import kotlinx.coroutines.experimental.launch
import org.kotlinacademy.data.SnippetData
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.ManagerRepository
import org.kotlinacademy.respositories.NewsRepository
import org.kotlinacademy.respositories.SnippetRepository
import kotlin.coroutines.experimental.CoroutineContext

class SnippetPresenter(
        private val uiContext: CoroutineContext,
        private val view: SnippetView,
        private val id: Int?,
        private val secret: String?,
        private val newsRepository: NewsRepository,
        private val managerRepository: ManagerRepository,
        private val snippetRepository: SnippetRepository
) : BasePresenter() {

    override fun onCreate() {
        if (id != null && secret != null) {
            view.loading = true
            jobs += launch(uiContext) {
                try {
                    val publishedSnippets = newsRepository.getNewsData().snippets
                    val unpublishedSnippets = managerRepository.getPropositions(secret).snippets
                    val snippet = (publishedSnippets + unpublishedSnippets).firstOrNull { it.id == id }
                    view.prefilled = snippet
                } catch (e: Throwable) {
                    view.showError(e)
                } finally {
                    view.loading = false
                }
            }
        }
    }

    fun onSubmitClicked(snippetData: SnippetData) {
        view.loading = true
        jobs += launch(uiContext) {
            try {
                val prefilled = view.prefilled
                if (prefilled != null && secret != null) {
                    val new = prefilled.copy(data = snippetData)
                    snippetRepository.update(new, secret)
                } else {
                    snippetRepository.propose(snippetData)
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