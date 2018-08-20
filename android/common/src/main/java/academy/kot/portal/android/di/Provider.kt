package academy.kot.portal.android.di

import org.kotlinacademy.respositories.*

abstract class Provider<T> {

    private val original by lazy { create() }
    var mock: T? = null

    abstract fun create(): T

    fun get(): T = mock ?: original
    fun lazyGet(): Lazy<T> = lazy { get() }
}

object NewsRepositoryDi: Provider<NewsRepository>() {
    override fun create() = NewsRepositoryImpl()
}

object FeedbackRepositoryDi: Provider<FeedbackRepository>() {
    override fun create() = FeedbackRepositoryImpl()
}

object NotificationRepositoryDi: Provider<NotificationRepository>() {
    override fun create() = NotificationRepositoryImpl()
}