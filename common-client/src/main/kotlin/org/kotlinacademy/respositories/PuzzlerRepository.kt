package org.kotlinacademy.respositories

import org.kotlinacademy.common.Provider
import org.kotlinacademy.data.Puzzler
import org.kotlinacademy.data.PuzzlerData

interface PuzzlerRepository {

    suspend fun propose(puzzler: PuzzlerData)
    suspend fun update(puzzler: Puzzler, secret: String)

    companion object : Provider<PuzzlerRepository>() {
        override fun create(): PuzzlerRepository = RepositoriesProvider.getPuzzlersRepository()
    }
}