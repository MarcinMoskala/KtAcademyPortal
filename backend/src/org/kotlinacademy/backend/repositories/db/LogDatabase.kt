package org.kotlinacademy.backend.repositories.db

import org.jetbrains.squash.statements.insertInto
import org.jetbrains.squash.statements.values
import org.kotlinacademy.backend.repositories.db.Database.makeTransaction
import org.kotlinacademy.now

class LogDatabase : LogDatabaseRepository {

    override suspend fun add(deviceType: String, userId: String, action: String, extra: String) = makeTransaction {
        insertInto(LogTable).values {
            it[this.deviceType] = deviceType
            it[this.userId] = userId
            it[this.action] = action
            it[this.extra] = extra
            it[this.dateTime] = now.toDateFormatString()
        }.execute()
    }
}