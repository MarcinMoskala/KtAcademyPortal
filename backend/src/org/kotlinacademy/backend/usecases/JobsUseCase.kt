package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.common.isToday
import org.kotlinacademy.backend.repositories.db.PuzzlersDatabaseRepository
import org.kotlinacademy.data.news

object JobsUseCase {

    suspend fun fillDayWithPuzzler() {
        if(nothingWasPublishedToday()) {
            publishPuzzlerFromTop()
        }
    }

    private suspend fun publishPuzzlerFromTop() {
        val puzzlersDatabaseRepository by PuzzlersDatabaseRepository.lazyGet()

        val puzzler = puzzlersDatabaseRepository.getPuzzlers()
                .filterNot { it.accepted }
                .minBy { it.dateTime } ?: return // No unaccepted puzzlers

        NewsUseCase.acceptPuzzler(puzzler.id)
    }

    private suspend fun nothingWasPublishedToday(): Boolean {
        val newsData = NewsUseCase.getAcceptedNewsData()
        return newsData.news().none { it.dateTime.isToday() }
    }
}