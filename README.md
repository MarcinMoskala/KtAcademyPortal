# KotlinAcademy application

Multiplatform client and server for (KotlinAcademy)[https://blog.kotlin-academy.com/].

## Backend

To run backend just use ` ./gradlew backend:run`. It is containing following methods:
* GET /news - returns list of news.
* PUT /news - used to send new news.

Use below commands to populate with data and check it server is correctly responding:

```
curl -X PUT -H "Content-Type: application/json" -d '{"title":"Multiplatform native development in Kotlin. Now with iOS!", "subtitle": "KotlinConf was a great event! There was a lot of inspiring ideas and a lot of announcements. Especially there was one huge announcement that excited everyone: Kotlin/Native started supporting iOS.", "imageUrl": "https://cdn-images-1.medium.com/max/800/1*M5erAXyih6ctSqcIW35ZjQ.png", "url":"https://blog.kotlin-academy.com/multiplatform-native-development-in-kotlin-now-with-ios-a8546f436eec"}' "http://localhost:8080/news"

curl -X PUT -H "Content-Type: application/json" -d '{"title":"Looool", "subtitle": "You cannot miss it!!!", "imageUrl": "http://religionnews.com/wp-content/uploads/2016/06/feature-3.jpg", "url":""}' "http://localhost:8080/news"

curl -X PUT "http://localhost:8080/news"
```

## Android

Android client is used to display list of news. It uses common-client logic to display loading, refreshing and obtaining list of elements.

## Web

Web client with the same logic like Android app. Currently not working yet.

## Desktop

Desktop client with the same logic like Android app. Currently not working yet.