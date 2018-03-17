package org.kotlinacademy.backend.repositories.db

import org.jetbrains.squash.definition.*

object InfosTable : TableDefinition() {
    val id = integer("id").autoIncrement().primaryKey()
    val title = text("title")
    val imageUrl = text("imageUrl")
    val description = text("description")
    val sources = text("sources")
    val url = text("url")
    val author = text("author")
    val authorUrl = text("authorUrl")
    val occurrence = text("dateTime")
    val accepted = bool("accepted")
}