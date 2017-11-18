package com.marcinmoskala.kotlinacademy.backend.db

import org.jetbrains.squash.definition.*

object NewsTable : TableDefinition() {
    val id = integer("id").autoIncrement().primaryKey()
    val title = varchar("title", 250)
    val subtitle = varchar("subtitle", 500)
    val imageUrl = varchar("imageUrl", 500)
    val url = varchar("url", 500)
}