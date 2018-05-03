package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.logInfo
import org.kotlinacademy.backend.repositories.db.ArticlesDatabaseRepository
import org.kotlinacademy.backend.repositories.db.PuzzlersDatabaseRepository
import org.kotlinacademy.backend.repositories.network.medium.MediumRepository
import org.kotlinacademy.data.*
import org.kotlinacademy.now

object MediumUseCase {

    suspend fun sync() {
        val mediumRepository = MediumRepository.get()
        val articlesDatabaseRepository = ArticlesDatabaseRepository.get()

        val news = mediumRepository.getPosts()
                ?.filterNot { it.isWeeklyPuzzlerTitle() }
        if (news == null || news.isEmpty()) {
            logInfo("Medium did not succeed when processing request")
            return
        }

        val prevNewsTitles = articlesDatabaseRepository.getArticles().map { it.title }
        news.filter { it.title !in prevNewsTitles }
                .forEach { articleData ->
                    NewsUseCase.addArticle(articleData)
                    PromotionUseCase.proposePromotion(articleData)
                }
    }

    suspend fun proposePostWithLastWeekPuzzlers() {
        val mediumRepository = MediumRepository.get()
        val puzzlersDatabaseRepository = PuzzlersDatabaseRepository.get()

        val lastWeek = now.plusDays(-7)
        val newPuzzlers = puzzlersDatabaseRepository.getPuzzlers()
                .filter { it.dateTime > lastWeek && it.accepted }

        if (newPuzzlers.size <= 1) {
            logInfo("Not enough new puzzlers to publish")
            return
        }

        val prevPosts = mediumRepository.getPosts().orEmpty()
        val nextTitle = nextWeeklyPuzzlersPostTitle(prevPosts)

        val body = makeWeeklyPuzzlersPostBodyInMarkdown(nextTitle, newPuzzlers)

        mediumRepository.postPost(nextTitle, body, listOf("Kotlin", "Programming Languages"))
    }

    fun nextWeeklyPuzzlersPostTitle(articles: List<ArticleData>): String {
        val biggestNumber = articles
                .filter { it.isWeeklyPuzzlerTitle() }
                .map { it.title.replace(namingPattern) { it.groupValues[1] }.toInt() }
                .max() ?: 0

        val new = biggestNumber + 1
        return weeklyPuzzlersNamePattern.replace(weeklyPuzzlersNameGroup, "$new")
    }

    fun ArticleData.isWeeklyPuzzlerTitle() = title matches namingPattern

    fun makeWeeklyPuzzlersPostBodyInMarkdown(title: String, puzzlers: List<Puzzler>): String {
        val intro = "Time for new battery of Kotlin puzzlers from Kotlin Academy! Have fun ;)"
        val questions = puzzlers.joinToString(separator = "\n") {
            """## ${it.title}
                |```
                |${it.codeQuestion}
                |```
                |${if (it.author == null && it.authorUrl == null) "" else if (it.authorUrl == null) "Author: ${it.author}" else "Author: [${it.author}](${it.authorUrl})"}
                |
                |What will it display? Some possibilities:
                |
                |${it.answers.replace("\\n".toRegex(), "<br/>")}
                |
                |Check out answer and explanation using [this link](${it.getTagUrl()}) or by reading this article till the end.
            """.trimMargin()
        }

        val answers = puzzlers.joinToString(separator = "\n") {
            """For "**${it.title}**" the correct answer is:
                |
                |${it.correctAnswer}
                |
                |Why? Here is an explanation:
                |
                |> ${it.explanation}
                |
            """.trimMargin()
        }

        val bottom = """
            |***
            |
            |Do you have your own idea for a puzzler? Submit it to [Kotlin Academy portal](http://portal.kotlin-academy.com/).
            |
            |Do you want more puzzlers in the future? Track [Kotlin Academy portal](http://portal.kotlin-academy.com/) or [subscribe to our mailing list](https://kotlin-academy.us17.list-manage.com/subscribe?u=5d3a48e1893758cb5be5c2919&id=d2ba84960a).
            |
            |[![Subscribe to newsletter](https://cdn-images-1.medium.com/max/1000/1*uDqv_d5NZnPUJA0FeZqhqQ.png)](https://kotlin-academy.us17.list-manage.com/subscribe?u=5d3a48e1893758cb5be5c2919&id=d2ba84960a)
            |
            |To be up-to-date with great news on [Kotlin Academy](http://portal.kotlin-academy.com/), [subscribe to the newsletter](https://kotlin-academy.us17.list-manage.com/subscribe?u=5d3a48e1893758cb5be5c2919&id=d2ba84960a), [observe Twitter and follow](https://twitter.com/kotlin_academy).
            |
            |If you like it, remember to clap. Note that if you hold the clap button, you can leave more claps.
        """.trimMargin()

        return "# $title\n" +
                "$intro\n" +
                "$questions\n" +
                "# Answers and explanations\n" +
                "$answers\n" +
                bottom
    }

    private const val weeklyPuzzlersNameGroup = "(\\d)*"
    private const val weeklyPuzzlersNamePattern = "Puzzlers on Kotlin Academy, week $weeklyPuzzlersNameGroup"
    private val namingPattern = weeklyPuzzlersNamePattern.toRegex()
}