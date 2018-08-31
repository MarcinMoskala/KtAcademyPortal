package org.kotlinacademy.backend.repositories.db

import org.jetbrains.squash.definition.*

object SnippetTable : TableDefinition() {
    val id = integer("id").autoIncrement().primaryKey()
    val title = text("title").nullable()
    val code = text("code")
    val explanation = text("explanation").nullable()
    val author = text("author").nullable()
    val authorUrl = text("authorUrl").nullable()
    val dateTime = text("dateTime")
    val accepted = bool("accepted")
}