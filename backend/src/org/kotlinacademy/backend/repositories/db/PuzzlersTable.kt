package org.kotlinacademy.backend.repositories.db

import org.jetbrains.squash.definition.*

object PuzzlersTable : TableDefinition() {
    val id = integer("id").autoIncrement().primaryKey()
    val title = text("title")
    val level = text("level")
    val actualQuestion = text("actualQuestion")
    val codeQuestion = text("question")
    val answers = text("answers")
    val author = text("author").nullable()
    val correctAnswer = text("correctAnswer")
    val explanation = text("explanation")
    val authorUrl = text("authorUrl").nullable()
    val dateTime = text("dateTime")
    val accepted = bool("accepted")
}