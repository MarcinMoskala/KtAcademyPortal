package academy.kot.portal.android

import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import org.kotlinacademy.data.Article
import org.kotlinacademy.data.Info
import org.kotlinacademy.data.News
import org.kotlinacademy.data.Puzzler
import org.kotlinacademy.respositories.NewsLocalRepository

object PrefsCommon : PreferenceHolder(), NewsLocalRepository {

    var articles: Map<Int, Article>? by bindToPreferenceFieldNullable("ARTICLES_LOCAL_KEY_2")
    var puzzlers: Map<Int, Puzzler>? by bindToPreferenceFieldNullable("PUZZLERS_LOCAL_KEY_2")
    var info: Map<Int, Info>? by bindToPreferenceFieldNullable("INFO_LOCAL_KEY_2")

    override fun getNews(): List<News>? {
        val articles = articles ?: return null
        val puzzlers = puzzlers ?: return null
        val info = info ?: return null
        val indexed: List<Pair<Int, News>> = articles.toPairs() + puzzlers.toPairs() + info.toPairs()
        val newsOrdered = indexed.sortedBy { it.first }.map { it.second }
        return newsOrdered
    }

    override fun setNews(news: List<News>) {
        val newsIndexed = news.mapIndexed { i, e -> i to e }
        articles = newsIndexed.filterInstanceOfAsIndexedMap()
        puzzlers = newsIndexed.filterInstanceOfAsIndexedMap()
        info = newsIndexed.filterInstanceOfAsIndexedMap()
    }

    private fun <K, V> Map<K, V>.toPairs() = map { (k, v) -> k to v }

    private inline fun <T1, T2, reified RT2: T2> List<Pair<T1, T2>>.filterInstanceOfAsIndexedMap(): Map<T1, RT2>
            = filter { it.second is RT2 }.filterIsInstance<Pair<T1, RT2>>().toMap()
}