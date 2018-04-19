package org.kotlinacademy.respositories

import kotlinx.coroutines.experimental.Deferred
import org.kotlinacademy.Endpoints.feedback
import org.kotlinacademy.common.HttpError
import org.kotlinacademy.data.Feedback
import retrofit2.HttpException
import retrofit2.http.Body
import retrofit2.http.POST

class FeedbackRepositoryImpl : FeedbackRepository {

    private val api: Api = retrofit.create(Api::class.java)

    override suspend fun addFeedback(feedback: Feedback) {
        try {
            api.postComment(feedback).await()
        } catch (t: HttpException) {
            throw HttpError(t.code(), t.message())
        }
    }

    interface Api {

        @POST(feedback)
        fun postComment(@Body feedback: Feedback): Deferred<String>
    }
}