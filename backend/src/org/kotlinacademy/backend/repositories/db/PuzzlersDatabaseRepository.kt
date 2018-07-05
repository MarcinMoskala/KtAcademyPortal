package org.kotlinacademy.backend.repositories.db

import org.kotlinacademy.backend.common.Provider
import org.kotlinacademy.data.Puzzler
import org.kotlinacademy.data.PuzzlerData

interface PuzzlersDatabaseRepository {

    suspend fun getPuzzlers(): List<Puzzler>
    suspend fun getPuzzler(id: Int): Puzzler
    suspend fun addPuzzler(puzzlerData: PuzzlerData, isAccepted: Boolean): Puzzler
    suspend fun deletePuzzler(id: Int)
    suspend fun updatePuzzler(puzzler: Puzzler)

    companion object: Provider<PuzzlersDatabaseRepository>() {
        override fun create(): PuzzlersDatabaseRepository = Database.puzzlersDatabase
    }
}