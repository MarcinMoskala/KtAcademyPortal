package org.kotlinacademy.mobile

import org.kotlinacademy.DateTime
import org.kotlinacademy.common.delay
import org.kotlinacademy.data.Feedback
import org.kotlinacademy.data.FirebaseTokenType
import org.kotlinacademy.data.News
import org.kotlinacademy.data.NewsData
import org.kotlinacademy.respositories.FeedbackRepository
import org.kotlinacademy.respositories.NewsRepository
import org.kotlinacademy.respositories.NotificationRepository
import kotlinx.coroutines.experimental.android.UI as AndroidUI

fun setUpServer() {
    NewsRepository.override = object : NewsRepository {
        suspend override fun getNewsData(): NewsData {
            delay(1000)
            val news = listOf(
                    News(5, "Programmer dictionary: Receiver type vs Receiver object", "In previous part we’ve already described difference between object and type. Similar differentiation is used for receivers in extension…", "https://cdn-images-1.medium.com/max/640/0*uGbdHQlU2qyf0VKA.jpg", "https://blog.kotlin-academy.com/programmer-dictionary-receiver-type-vs-receiver-object-575d2705ddd9", DateTime(1513233179000)),
                    News(4, "Architecture for Multiplatform native development in Kotlin", "My mentor was often saying “If you are using Ctrl-C Ctrl-V in a single project, you are doing something wrong”. This sentence got deeply…", "https://cdn-images-1.medium.com/max/640/0*qZfgAQ8us1w9z3Z9.", "https://blog.kotlin-academy.com/architecture-for-multiplatform-development-in-kotlin-cc770f4abdfd", DateTime(1512973765000)),
                    News(3, "Programmer dictionary: Extension receiver vs Dispatch receiver", "Extension receiver is the receiver that is closely related to Kotlin extensions. It represents an object that we define an extension for.", "https://cdn-images-1.medium.com/max/640/1*RBntJ9Ivhd4ZW4K-qQSjPQ.jpeg", "https://blog.kotlin-academy.com/programmer-dictionary-extension-receiver-vs-dispatch-receiver-cd154e57e277", DateTime(1512626599000)),
                    News(2, "Lessons from my first multiplatform Kotlin project", "I have just finished Kotlin multiplatform project with 3 different clients:", "https://cdn-images-1.medium.com/max/640/0*QCqRjzCc_QPux8C7.png", "https://blog.kotlin-academy.com/lessons-from-my-first-multiplatform-kotlin-project-d4e311f15874", DateTime(1512366097000)),
                    News(1, "Programmer dictionary: Implicit receiver vs explicit receiver", "The concept of receivers was previously explained, so make sure you know what receiver is before reading further.", "https://cdn-images-1.medium.com/max/640/1*X1QcX65-iUGtBWy2yw302g.jpeg", "https://blog.kotlin-academy.com/programmer-dictionary-implicit-receiver-vs-explicit-receiver-da638de31f3c", DateTime(1512021648000))
            )
            return NewsData(news = news)
        }
    }
    FeedbackRepository.override = object : FeedbackRepository {
        suspend override fun addFeedback(feedback: Feedback) {
            delay(1000)
            // no-op
        }
    }
    NotificationRepository.override = object : NotificationRepository {
        suspend override fun registerToken(token: String, type: FirebaseTokenType) {
            delay(1000)
            // no-op
        }
    }
}