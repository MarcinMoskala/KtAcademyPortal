package com.marcinmoskala.kotlinacademy.backend.db

import org.jetbrains.squash.definition.*

object CommentsTable : TableDefinition() {
    val id = integer("id").autoIncrement().primaryKey()
    val newsId = integer("newsId").nullable()
    val rating = integer("rating")
    val commentText = text("comment")
    val suggestionsText = text("suggestions")
}