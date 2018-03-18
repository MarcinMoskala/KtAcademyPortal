import org.kotlinacademy.backend.repositories.network.dto.NotificationResult
import org.kotlinacademy.data.*
import org.kotlinacademy.parseDateTime

val someFeedback = Feedback(1, 10, "Some comment", "Some suggestions")
val someNotificationResult = NotificationResult(222, 12)
val someNotificationResult2 = NotificationResult(21, 333)
const val someEmail = "some@email.com"
const val adminEmail = "admin@email.com"
val someInfo = Info(1, "Great info", "Image url", "Description", "Some sources", "urk", "Author", "Author url", "2018-10-12T12:00:01".parseDateTime())
val somePuzzler = Puzzler(1, "Great puzzler", "Question", listOf(), "Author", "Author url", "2018-10-12T12:00:01".parseDateTime())
val newNews = Article(-1, "Article title", "Article subtitle", "Image url", "Url", "2018-10-12T12:00:01".parseDateTime())
val someNews = Article(2, "Article title", "Article subtitle", "Image url", "Url", "2018-10-12T12:00:01".parseDateTime())
val someNews2 = Article(3, "Article title 2", "Article subtitle 2", "Image url 2", "Url 2", "2018-10-12T12:00:02".parseDateTime())
val someFirebaseTokenData = FirebaseTokenData("AAAA", FirebaseTokenType.Web)
val someFirebaseTokenData2 = FirebaseTokenData("BBB", FirebaseTokenType.Android)