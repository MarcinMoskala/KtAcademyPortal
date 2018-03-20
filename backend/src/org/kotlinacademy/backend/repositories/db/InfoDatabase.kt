package org.kotlinacademy.backend.repositories.db

import org.jetbrains.squash.connection.Transaction
import org.jetbrains.squash.expressions.eq
import org.jetbrains.squash.query.select
import org.jetbrains.squash.query.where
import org.jetbrains.squash.results.ResultRow
import org.jetbrains.squash.results.get
import org.jetbrains.squash.statements.*
import org.kotlinacademy.backend.repositories.db.Database.makeTransaction
import org.kotlinacademy.data.*
import org.kotlinacademy.now
import org.kotlinacademy.parseDateTime

class InfoDatabase : InfoDatabaseRepository {

    override suspend fun getInfos(): List<Info> = makeTransaction {
        selectInfos()
                .execute()
                .map(::toInfo)
                .toList()
    }

    override suspend fun getInfo(id: Int): Info = makeTransaction {
        selectInfos()
                .where { InfoTable.id.eq(id) }
                .execute()
                .map(::toInfo)
                .toList()
                .first()
    }

    override suspend fun addInfo(infoData: InfoData, isAccepted: Boolean): Info = makeTransaction {
        val id = insertInto(InfoTable).values {
            it[title] = infoData.title
            it[imageUrl] = infoData.imageUrl
            it[description] = infoData.description
            it[sources] = infoData.sources
            it[url] = infoData.url
            it[author] = infoData.author
            it[authorUrl] = infoData.authorUrl
            it[occurrence] = now.toDateFormatString()
            it[accepted] = isAccepted
        }.fetch(InfoTable.id).execute()

        Info(id, infoData, now, false)
    }

    override suspend fun deleteInfo(id: Int) = makeTransaction {
        deleteFrom(InfoTable).where(InfoTable.id.eq(id)).execute()
    }

    override suspend fun updateInfo(info: Info) = makeTransaction {
        val id = info.id
        require(countInfosWithId(id) == 1) { "Should be single info with id $id" }
        update(InfoTable)
                .where { InfoTable.id eq id }
                .set {
                    it[title] = info.title
                    it[imageUrl] = info.imageUrl
                    it[description] = info.description
                    it[sources] = info.sources
                    it[url] = info.url
                    it[author] = info.author
                    it[authorUrl] = info.authorUrl
                    it[occurrence] = info.dateTime.toDateFormatString()
                    it[accepted] = info.accepted
                }.execute()
    }

    private fun selectInfos() = InfoTable.select(InfoTable.id, InfoTable.title, InfoTable.imageUrl, InfoTable.description, InfoTable.sources, InfoTable.url, InfoTable.author, InfoTable.authorUrl, InfoTable.occurrence, InfoTable.accepted)

    private fun toInfo(it: ResultRow): Info = Info(
            id = it[InfoTable.id],
            data = InfoData(
                    title = it[InfoTable.title],
                    imageUrl = it[InfoTable.imageUrl],
                    description = it[InfoTable.description],
                    sources = it[InfoTable.sources],
                    url = it[InfoTable.url],
                    author = it[InfoTable.author],
                    authorUrl = it[InfoTable.authorUrl]
            ),
            dateTime = it[InfoTable.occurrence].parseDateTime(),
            accepted = it[InfoTable.accepted]
    )

    private fun Transaction.countInfosWithId(id: Int) = InfoTable.select(InfoTable.id)
            .where { InfoTable.id.eq(id) }
            .execute()
            .count()
}