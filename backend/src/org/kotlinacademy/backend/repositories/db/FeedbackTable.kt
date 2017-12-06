package org.kotlinacademy.backend.repositories.db

import org.jetbrains.squash.definition.*

object FeedbackTable : IntIdTable() {
    val id = integer("id").autoIncrement().primaryKey()
    val newsId = integer("newsId").nullable()
    val rating = integer("rating")
    val commentText = text("comment")
    val suggestionsText = text("suggestions")
}