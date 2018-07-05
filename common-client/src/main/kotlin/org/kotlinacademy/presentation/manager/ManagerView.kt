package org.kotlinacademy.presentation.manager

import org.kotlinacademy.data.News
import org.kotlinacademy.presentation.BaseView

interface ManagerView : BaseView {
    var loading: Boolean
    fun showList(news: List<News>)
}