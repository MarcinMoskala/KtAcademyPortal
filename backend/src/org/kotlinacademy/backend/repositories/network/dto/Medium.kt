package org.kotlinacademy.backend.repositories.network.dto

import org.kotlinacademy.DateTime
import org.kotlinacademy.data.ArticleData

class MediumPostsResponse(
        val success: Boolean,
        val payload: MediumPayload
)

class MediumPayload(
        val posts: List<MediumPost>
)

class MediumPost(
        val title: String,
        val virtuals: MediumVirtuals,
        val firstPublishedAt: Long,
        val uniqueSlug: String
)

class MediumVirtuals(
        val subtitle: String,
        val previewImage: MediumPreviewImage
)

class MediumPreviewImage(
        val imageId: String
)

fun MediumPostsResponse.toArticleData() = payload.posts.map(MediumPost::toArticleData)

fun MediumPost.toArticleData() = ArticleData(
        title = title,
        subtitle = virtuals.subtitle,
        imageUrl = "https://cdn-images-1.medium.com/max/640/" + virtuals.previewImage.imageId,
        url = "https://blog.kotlin-academy.com/$uniqueSlug",
        occurrence = DateTime(firstPublishedAt)
)