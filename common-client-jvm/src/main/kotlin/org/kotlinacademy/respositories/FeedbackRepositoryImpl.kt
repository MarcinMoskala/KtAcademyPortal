package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints.feedback
import org.kotlinacademy.common.HttpError
import org.kotlinacademy.data.Feedback
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.http.Body
import retrofit2.http.POST
import ru.gildor.coroutines.retrofit.await

class FeedbackRepositoryImpl : FeedbackRepository {

    private val api = retrofit.create(Api::class.java)!!

    suspend override fun addFeedback(feedback: Feedback) {
        try {
            api.postComment(feedback).await()
        } catch (t: HttpException) {
            throw HttpError(t.code(), t.message())
        }
    }

    interface Api {

        @POST(feedback)
        fun postComment(@Body feedback: Feedback): Call<String>
    }
}