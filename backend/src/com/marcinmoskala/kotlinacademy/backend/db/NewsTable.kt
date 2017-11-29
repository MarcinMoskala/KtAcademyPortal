package com.marcinmoskala.kotlinacademy.backend.db

import org.jetbrains.squash.definition.*

object NewsTable : TableDefinition() {
    val id = integer("id").autoIncrement().primaryKey()
    val title = text("title")
    val subtitle = text("subtitle")
    val imageUrl = text("imageUrl")
    val url = text("url")
}