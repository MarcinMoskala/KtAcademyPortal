package org.kotlinacademy.backend.repositories.db

import org.jetbrains.squash.connection.Transaction
import org.jetbrains.squash.expressions.eq
import org.jetbrains.squash.query.select
import org.jetbrains.squash.query.where
import org.jetbrains.squash.results.ResultRow
import org.jetbrains.squash.results.get
import org.jetbrains.squash.statements.*
import org.kotlinacademy.backend.repositories.db.Database.makeTransaction
import org.kotlinacademy.data.Info
import org.kotlinacademy.parseDateTime

class InfoDatabase : InfoDatabaseRepository {

    override suspend fun getInfos(): List<Info> = makeTransaction {
        selectInfos()
                .execute()
                .map(::toInfo)
                .toList()
    }

    override suspend fun getAcceptedInfos(): List<Info> = makeTransaction {
        selectInfos()
                .where { InfosTable.accepted.eq(true) }
                .execute()
                .map(::toInfo)
                .toList()
    }

    override suspend fun getInfo(id: Int): Info = makeTransaction {
        selectInfos()
                .where { InfosTable.id.eq(id) }
                .execute()
                .map(::toInfo)
                .toList()
                .first()
    }

    override suspend fun addInfo(info: Info, isAccepted: Boolean) = makeTransaction {
        insertInto(InfosTable).values {
            it[id] = info.id
            it[title] = info.title
            it[imageUrl] = info.imageUrl
            it[description] = info.description
            it[sources] = info.sources
            it[url] = info.url
            it[author] = info.author
            it[authorUrl] = info.authorUrl
            it[occurrence] = info.dateTime?.toDateFormatString()
            it[accepted] = isAccepted
        }.execute()
    }

    override suspend fun deleteInfo(id: Int) = makeTransaction {
        deleteFrom(InfosTable).where(InfosTable.id.eq(id)).execute()
    }

    override suspend fun updateInfo(id: Int, info: Info, isAccepted: Boolean?) = makeTransaction {
        require(countInfosWithId(id) == 1) { "Should be single info with id $id" }
        update(InfosTable)
                .where { InfosTable.id eq id }
                .set {
                    it[title] = info.title
                    it[imageUrl] = info.imageUrl
                    it[description] = info.description
                    it[sources] = info.sources
                    it[url] = info.url
                    it[author] = info.author
                    it[authorUrl] = info.authorUrl
                    it[occurrence] = info.dateTime?.toDateFormatString()
                    if (isAccepted != null) it[accepted] = isAccepted
                }.execute()
    }

    private fun selectInfos() = InfosTable.select(InfosTable.id, InfosTable.title, InfosTable.description, InfosTable.sources, InfosTable.url, InfosTable.author, InfosTable.authorUrl, InfosTable.occurrence)

    private fun toInfo(it: ResultRow): Info = Info(
            id = it[InfosTable.id],
            title = it[InfosTable.title],
            imageUrl = it[InfosTable.imageUrl],
            description = it[InfosTable.description],
            sources = it[InfosTable.sources],
            url = it[InfosTable.url],
            author = it[InfosTable.author],
            authorUrl = it[InfosTable.authorUrl],
            dateTime = it[InfosTable.occurrence].parseDateTime()
    )

    private fun Transaction.countInfosWithId(id: Int) = InfosTable.select(InfosTable.id)
            .where { InfosTable.id.eq(id) }
            .execute()
            .count()
}