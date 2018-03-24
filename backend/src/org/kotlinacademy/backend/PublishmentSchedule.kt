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
        ),
        DateTime(0, 0, 12, 27, 12, 2018) to PuzzlerData(
                title = "If-else chaining",
                level = "Medium",
                question = """
                |fun printNumberSign(num: Int) {
                |    if (num < 0) {
                |         "negative"
                |    } else if (num > 0) {
                |         "positive"
                |    } else {
                |         "zero"
                |    }.let { print(it) }
                |}
                |
                |printNumberSign(-2)
                |print(",")
                |printNumberSign(0)
                |print(",")
                |printNumberSign(2)""".trimMargin(),
                answers = """
                |What will it print?
                |a) negative,zero,positive
                |b) negative,zero,
                |c) negative,,positive
                |d) ,zero,positive""".trimMargin(),
                correctAnswer = "d) ,zero,positive",
                explanation = "Remember that the `else if` construct is really just calling `else` with a \"single-line\" `if`, which is the rest of your expression\n" +
                        "+* In Kotlin, functions are resolved before if-else blocks\n" +
                        "+* So the `.let { print(it) }` is only applying to the final `else if` and `else` branches\n" +
                        "+* So in this case, the result of the first `if` statement will not be used and the function will return immediately\n" +
                        "+* To avoid this situation, you can wrap the entire `if ... else ...` in parens, and then chain `.let` on it",
                author = "Kevin Most",
                authorUrl = "https://github.com/angryziber/kotlin-puzzlers/blob/master/src/syntax/weirdChaining/WeirdChaining.kts"
        )

)