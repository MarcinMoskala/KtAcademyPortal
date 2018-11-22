import org.kotlinacademy.backend.repositories.network.notifications.NotificationResult
import org.kotlinacademy.data.*
import org.kotlinacademy.parseDateTime

val someFeedback = Feedback(1, 10, "Some comment", "Some suggestions")
val someNotificationResult = NotificationResult(222, 12)
val someNotificationResult2 = NotificationResult(21, 333)
const val someEmail = "some@email.com"
const val adminEmail = "admin@email.com"
val someInfoData = InfoData("Great info", "Image url", "Description", "Some sources", "urk", "Author", "Author url")
val someInfoAccepted = Info(1, someInfoData, "2999-10-12T12:00:01".parseDateTime(), true)
val someInfoUnaccepted = Info(2, someInfoData, "2999-10-12T12:00:01".parseDateTime(), false)
val somePuzzlerData = PuzzlerData("Great puzzler", "Basic", "Question 1", "Code Question 1", "Code Question 1", "Possible answers", "Correct answer", "Explanation", "Author", "Author url")
val somePuzzlerData2 = PuzzlerData("Great puzzler 2", "Basic 2", "Question 2", "Code question 2", "Code question 2", "Possible answers 2", "Correct answer 2", "Explanation 2", "Author 2", "Author url 2")
val somePuzzlerUnaccepted = Puzzler(1, somePuzzlerData, "2999-10-12T12:00:01".parseDateTime(), false)
val somePuzzlerAccepted = Puzzler(2, somePuzzlerData, "2999-10-12T12:00:01".parseDateTime(), true)
val somePuzzlerAccepted2 = Puzzler(2, somePuzzlerData2, "2999-10-12T12:00:01".parseDateTime(), true)
val someArticleData = ArticleData("Article title", "Article subtitle", "Image url", "Url", "2999-10-12T12:00:01".parseDateTime())
val someArticle = Article(1, someArticleData)
val someWeeklyPuzzlersArticleData = someArticleData.copy(title = "Puzzlers on Kotlin Academy, week 1")
val someWeeklyPuzzlersArticleData2 = someArticleData.copy(title = "Puzzlers on Kotlin Academy, week 2")
val someArticleData2 = ArticleData("Article title 2", "Article subtitle 2", "Image url 2", "Url 2", "2999-10-12T12:00:02".parseDateTime())
val someArticle2 = Article(2, someArticleData2)
val someFirebaseTokenData = FirebaseTokenData("AAAA", FirebaseTokenType.Web)
val someFirebaseTokenData2 = FirebaseTokenData("BBB", FirebaseTokenType.Android)