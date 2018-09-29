package org.kotlinacademy

import org.kotlinacademy.data.*

// TODO Should be one with backend
val someFeedback = Feedback(1, 10, "Some comment", "Some suggestions")

const val someToken = "Token"
val someTokenType = FirebaseTokenType.Android
val someInfoData = InfoData("Great info", "Image url", "Description", "Some sources", "urk", "Author", "Author url")
val someInfo = Info(1, someInfoData, "2018-10-14T12:00:01".parseDateTime(), true)
val somePuzzlerData = PuzzlerData("Great puzzler", "Basic", "Question 1", "Code Question 1", "Code Question 1", "Possible answers", "Correct answer", "Explanation", "Author", "Author url")
val somePuzzlerData2 = PuzzlerData("Great puzzler 2", "Basic 2", "Question 2", "Code question 2", "Code question 2", "Possible answers 2", "Correct answer 2", "Explanation 2", "Author 2", "Author url 2")
val somePuzzler = Puzzler(2, somePuzzlerData, "2018-10-12T12:00:01".parseDateTime(), true)
val somePuzzler2 = Puzzler(2, somePuzzlerData2, "2018-10-11T12:00:01".parseDateTime(), true)

val someArticle1 = Article(1, ArticleData("Some title", "Description", "Image url", "Url", "2018-10-13T12:00:01".parseDateTime()))
val someArticle2 = Article(2, ArticleData("Some title 2", "Description 2", "Image url 2", "Url 2", "2018-10-12T12:00:01".parseDateTime()))
val someNewsList = listOf(someArticle1, someInfo, somePuzzler, somePuzzler2, someArticle2)
val someArticlesList2Sorted = listOf(someArticle1, someArticle2)
val someArticlesList2Unsorted = listOf(someArticle2, someArticle1)

val someNewsData = NewsData(
        articles = listOf(someArticle1, someArticle2),
        infos = listOf(someInfo),
        puzzlers = listOf(somePuzzler, somePuzzler2)
)

val someError = Throwable()