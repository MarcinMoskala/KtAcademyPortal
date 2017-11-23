package com.marcinmoskala.kotlinacademy.respositories

import com.marcinmoskala.kotlinacademy.Endpoints
import com.marcinmoskala.kotlinacademy.Endpoints.comments
import com.marcinmoskala.kotlinacademy.common.HttpError
import com.marcinmoskala.kotlinacademy.data.Comment
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.data.NewsData
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.await

class CommentRepositoryImpl : CommentRepository {

    private val api = retrofit.create(Api::class.java)!!

    suspend override fun addComment(comment: Comment) {
        try {
            api.postComment(comment).await()
        } catch (t: HttpException) {
            throw HttpError(t.code(), t.message())
        }
    }

    interface Api {

        @POST(comments)
        fun postComment(@Body comment: Comment): Call<String>
    }
}