package com.marcinmoskala.kotlinacademy.respositories

import com.marcinmoskala.kotlinacademy.Endpoints.feedback
import com.marcinmoskala.kotlinacademy.common.HttpError
import com.marcinmoskala.kotlinacademy.data.Feedback
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.http.Body
import retrofit2.http.POST
import ru.gildor.coroutines.retrofit.await

class CommentRepositoryImpl : CommentRepository {

    private val api = retrofit.create(Api::class.java)!!

    suspend override fun addComment(feedback: Feedback) {
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