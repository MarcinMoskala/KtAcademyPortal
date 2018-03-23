package org.kotlinacademy.backend

import org.kotlinacademy.DateTime
import org.kotlinacademy.data.PuzzlerData

fun getSchedule() = mapOf(
        DateTime(0, 0, 12, 24, 12, 2018) to PuzzlerData(
                title = "Indent trimming",
                level = "Basic",
                question = """
                |val world = "multiline world"
                |println(""${'"'}
                |    Hello
                |    \${'$'}world
                |""${'"'}.trimIndent())""".trimMargin(),
                answers = """
                |A) >Hello
                |   >${'$'}world
                |B) >
                |   >      Hello
                |   >      ${'$'}world
                |   >
                |C) >Hello
                |   >\multiline world
                |D) doesn't compile""".trimMargin(),
                correctAnswer = "Hello\n" +
                        "\\multiline world",
                explanation = "A raw string is delimited by a triple quote (\"\"\"), contains no escaping and can contain newlines and any other characters. Although dollar cannot be used there even with escape character \\. If we want to use \$, then we need to use \${'$'} instead",
                author = "Dmitry Kandalov",
                authorUrl = "https://github.com/dkandalov/kotlin-puzzlers/blob/master/puzzlers/1-hello-multiline-world.kts"
        )
)