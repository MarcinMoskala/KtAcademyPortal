package org.kotlinacademy.backend.repositories.db

import org.jetbrains.squash.definition.*

object NewsTable : IntIdTable() {
    val id = integer("id").autoIncrement().primaryKey()
    val title = text("title")
    val subtitle = text("subtitle")
    val imageUrl = text("imageUrl")
    val url = text("url")
    val occurrence = text("occurrence")
}