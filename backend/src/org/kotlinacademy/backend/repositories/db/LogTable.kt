package org.kotlinacademy.backend.repositories.db

import org.jetbrains.squash.definition.*

object LogTable : TableDefinition() {
    val id = integer("id").autoIncrement().primaryKey()
    val deviceType = text("deviceType")
    val userId = text("userId")
    val action = text("action")
    val extra = text("extra").nullable()
    val dateTime = text("dateTime")
}