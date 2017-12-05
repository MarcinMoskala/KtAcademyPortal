package org.kotlinacademy.backend.repositories.network.dto

import org.kotlinacademy.DateTime
import org.kotlinacademy.data.News

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

fun MediumPostsResponse.toNews() = payload.posts.map(MediumPost::toNews)

fun MediumPost.toNews() = News(
        title = title,
        subtitle = virtuals.subtitle,
        imageUrl = "https://cdn-images-1.medium.com/max/640/" + virtuals.previewImage.imageId,
        url = "https://blog.kotlin-academy.com/" + uniqueSlug,
        occurrence = DateTime(firstPublishedAt)
)