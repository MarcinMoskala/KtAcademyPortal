package org.kotlinacademy.backend.repositories.db

import org.jetbrains.squash.query.select
import org.jetbrains.squash.results.get
import org.jetbrains.squash.statements.insertInto
import org.jetbrains.squash.statements.values
import org.kotlinacademy.backend.repositories.db.Database.makeTransaction
import org.kotlinacademy.data.FirebaseTokenData
import org.kotlinacademy.data.FirebaseTokenType

class TokenDatabase : TokenDatabaseRepository {

    override suspend fun getAllTokens(): List<FirebaseTokenData> = makeTransaction {
        TokensTable.select(TokensTable.token, TokensTable.type)
                .execute()
                .map { FirebaseTokenData(it[TokensTable.token], it[TokensTable.type].toFirebaseTokenType()) }
                .toList()
    }

    override suspend fun addToken(tokenText: String, tokenType: FirebaseTokenType) = makeTransaction {
        insertInto(TokensTable).values {
            it[type] = tokenType.toValueName()
            it[token] = tokenText
        }.execute()
    }

    private fun FirebaseTokenType.toValueName(): String = when (this) {
        FirebaseTokenType.Web -> "web"
        FirebaseTokenType.Android -> "android"
    }

    private fun String.toFirebaseTokenType(): FirebaseTokenType = when (this) {
        "web" -> FirebaseTokenType.Web
        "android" -> FirebaseTokenType.Android
        else -> throw Error("Illegal type $this set as furebase token type")
    }
}