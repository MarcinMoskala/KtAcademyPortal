package org.kotlinacademy.respositories

import org.kotlinacademy.common.Provider
import org.kotlinacademy.data.Puzzler

interface PuzzlerRepository {

    suspend fun propose(puzzler: Puzzler)

    companion object : Provider<PuzzlerRepository>() {
        override fun create(): PuzzlerRepository = RepositoriesProvider.getPuzzlersRepository()
    }
}