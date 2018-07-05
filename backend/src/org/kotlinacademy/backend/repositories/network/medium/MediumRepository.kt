package org.kotlinacademy.backend.repositories.network.medium

import kotlinx.coroutines.experimental.Deferred
import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.common.Provider
import org.kotlinacademy.backend.repositories.network.makeRetrofit
import org.kotlinacademy.data.ArticleData
import org.kotlinacademy.fromJson
import retrofit2.http.*

interface MediumRepository {

    suspend fun getPosts(): List<ArticleData>?

    suspend fun postPost(title: String, bodyInMarkdown: String, tags: List<String>)

    class MediumRepositoryImpl : MediumRepository {
        private val api: Api = makeRetrofit("https://medium.com/").create(Api::class.java)

        override suspend fun getPosts(): List<ArticleData>? =
                api.getPlainPosts()
                        .await()
                        .dropMediumFakeStart()
                        .fromJson<MediumPostsResponse>()
                        .takeIf { it != null && it.success }
                        ?.toArticleData()

        override suspend fun postPost(title: String, bodyInMarkdown: String, tags: List<String>) {
            api.postPost(
                    bearerToken = "Bearer ${Config.mediumToken}",
                    postData = MediumNewPost(
                            title = title,
                            contentFormat = "markdown",
                            content = bodyInMarkdown,
                            tags = tags,
                            publishStatus = "draft"
                    )
            )
        }

        // Needed because of Medium API policy https://github.com/Medium/medium-api-docs/issues/115
        private fun String.dropMediumFakeStart() = dropWhile { it != '{' }
    }

    interface Api {

        @Headers("Accept: application/json")
        @GET("kotlin-academy/latest?count=1000")
        fun getPlainPosts(): Deferred<String>

        @Headers("Accept: application/json")
        @POST("v1/publications/$publicationId/posts")
        fun postPost(@Header("Authorization") bearerToken: String, @Body postData: MediumNewPost): Deferred<String>

        companion object {
            const val publicationId = "e57b304801ef"
        }
    }

    companion object : Provider<MediumRepository>() {
        override fun create(): MediumRepository = MediumRepositoryImpl()
    }
}
