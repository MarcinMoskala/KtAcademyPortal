package org.kotlinacademy.common

import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import org.kotlinacademy.data.Article
import org.kotlinacademy.data.Info
import org.kotlinacademy.data.News
import org.kotlinacademy.data.Puzzler
import org.kotlinacademy.respositories.NewsLocalRepository

object PrefsCommon : PreferenceHolder(), NewsLocalRepository {

    var articles: List<Pair<Int, Article>>? by bindToPreferenceFieldNullable("ARTICLES_LOCAL_KEY")
    var puzzlers: List<Pair<Int, Puzzler>>? by bindToPreferenceFieldNullable("PUZZLERS_LOCAL_KEY")
    var info: List<Pair<Int, Info>>? by bindToPreferenceFieldNullable("INFO_LOCAL_KEY")

    override fun getNews(): List<News>? {
        val articles = articles ?: return null
        val puzzlers = puzzlers ?: return null
        val info = info ?: return null
        return articles.map { it.second } + puzzlers.map { it.second } + info.map { it.second }
    }

    override fun setNews(news: List<News>) {
        val newsIndexed = news.mapIndexed { i, e -> i to e }
        articles = newsIndexed.filterSecondIs()
        puzzlers = newsIndexed.filterSecondIs()
        info = newsIndexed.filterSecondIs()
    }
}