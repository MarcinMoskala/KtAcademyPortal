package org.kotlinacademy.backend.repositories.db

import org.kotlinacademy.common.Provider
import org.kotlinacademy.data.*

interface PuzzlersDatabaseRepository {

    suspend fun getPuzzlers(): List<Puzzler>
    suspend fun getAcceptedPuzzlers(): List<Puzzler>
    suspend fun getPuzzler(id: Int): Puzzler
    suspend fun addPuzzler(puzzler: Puzzler, isAccepted: Boolean)
    suspend fun deletePuzzler(id: Int)
    suspend fun updatePuzzler(id: Int, puzzler: Puzzler, isAccepted: Boolean? = null)

    companion object: Provider<PuzzlersDatabaseRepository>() {
        override fun create(): PuzzlersDatabaseRepository = Database.puzzlersDatabase
    }
}