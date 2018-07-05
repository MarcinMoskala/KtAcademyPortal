package org.kotlinacademy.mobile

import org.kotlinacademy.DateTime
import org.kotlinacademy.common.delay
import org.kotlinacademy.common.di.FeedbackRepositoryDi
import org.kotlinacademy.common.di.NewsRepositoryDi
import org.kotlinacademy.common.di.NotificationRepositoryDi
import org.kotlinacademy.data.*
import org.kotlinacademy.respositories.FeedbackRepository
import org.kotlinacademy.respositories.NewsRepository
import org.kotlinacademy.respositories.NotificationRepository

fun setUpBaseUrlOrMock() {
    NewsRepositoryDi.mock = object : NewsRepository {
        override suspend fun getNewsData(): NewsData {
            delay(1000)
            val news = listOf(
                    Article(5, ArticleData("Programmer dictionary: Receiver type vs Receiver object", "In previous part we’ve already described difference between object and type. Similar differentiation is used for receivers in extension…", "https://cdn-images-1.medium.com/max/640/0*uGbdHQlU2qyf0VKA.jpg", "https://blog.kotlin-academy.com/programmer-dictionary-receiver-type-vs-receiver-object-575d2705ddd9", DateTime(1513233179000))),
                    Article(4, ArticleData("Architecture for Multiplatform native development in Kotlin", "My mentor was often saying “If you are using Ctrl-C Ctrl-V in a single project, you are doing something wrong”. This sentence got deeply…", "https://cdn-images-1.medium.com/max/640/0*qZfgAQ8us1w9z3Z9.", "https://blog.kotlin-academy.com/architecture-for-multiplatform-development-in-kotlin-cc770f4abdfd", DateTime(1512973765000))),
                    Article(3, ArticleData("Programmer dictionary: Extension receiver vs Dispatch receiver", "Extension receiver is the receiver that is closely related to Kotlin extensions. It represents an object that we define an extension for.", "https://cdn-images-1.medium.com/max/640/1*RBntJ9Ivhd4ZW4K-qQSjPQ.jpeg", "https://blog.kotlin-academy.com/programmer-dictionary-extension-receiver-vs-dispatch-receiver-cd154e57e277", DateTime(1512626599000))),
                    Article(2, ArticleData("Lessons from my first multiplatform Kotlin project", "I have just finished Kotlin multiplatform project with 3 different clients:", "https://cdn-images-1.medium.com/max/640/0*QCqRjzCc_QPux8C7.png", "https://blog.kotlin-academy.com/lessons-from-my-first-multiplatform-kotlin-project-d4e311f15874", DateTime(1512366097000))),
                    Article(1, ArticleData("Programmer dictionary: Implicit receiver vs explicit receiver", "The concept of receivers was previously explained, so make sure you know what receiver is before reading further.", "https://cdn-images-1.medium.com/max/640/1*X1QcX65-iUGtBWy2yw302g.jpeg", "https://blog.kotlin-academy.com/programmer-dictionary-implicit-receiver-vs-explicit-receiver-da638de31f3c", DateTime(1512021648000)))
            )
            val infos = listOf(
                    Info(1, InfoData("Kotlin is now on Android documentation", "https://scontent.fotp3-1.fna.fbcdn.net/v/t1.0-9/29177641_10213153683650531_2721993385639411712_o.jpg?oh=44b1aacd4fc006504986ee6f8949fccf&oe=5B492F1B", "For some time, examples in Android documentation are both in Kotlin and in Java, with Kotlin chosen by default. Not all examples are moved yet, but they are systematically moving them. It looks like Kotlin is becoming not only first class Android development language, but it also becomes suggested development language.", "Android documentation\n https://developer.android.com/guide/index.html", null, "Marcin Moskala", "http://marcinmoskala.com/"), DateTime(1513233179500), true)
            )
            return NewsData(articles = news, infos = infos, puzzlers = emptyList())
        }
    }
    FeedbackRepositoryDi.mock = object : FeedbackRepository {
        override suspend fun addFeedback(feedback: Feedback) {
            delay(1000)
            // no-op
        }
    }
    NotificationRepositoryDi.mock = object : NotificationRepository {
        override suspend fun registerToken(token: String, type: FirebaseTokenType) {
            delay(1000)
            // no-op
        }
    }
}