package com.marcinmoskala.kotlinacademy.backend.db

import org.jetbrains.squash.definition.*

object TokensTable : TableDefinition() {
    val id = integer("id").autoIncrement().primaryKey()
    val type = varchar("title", 10)
    val token = text("subtitle")

    enum class Types {
        Web
    }
}