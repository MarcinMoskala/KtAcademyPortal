package org.kotlinacademy.backend.repositories.db

import org.jetbrains.squash.definition.*

object InfoTable : TableDefinition() {
    val id = integer("id").autoIncrement().primaryKey()
    val title = text("title")
    val imageUrl = text("imageUrl")
    val description = text("description")
    val sources = text("sources")
    val url = text("url").nullable()
    val author = text("author").nullable()
    val authorUrl = text("authorUrl").nullable()
    val occurrence = text("dateTime")
    val accepted = bool("accepted")
}