# KotlinAcademy application

This is an example how multiplatform development can be used to effectively extrac and reuse logic. 
It contains multiple clients that implement the same logic and single backend.
The only common part for all this project is Data Model, so it is placed in `common` module which is shared among all the platforms.
All client applications are based on MVP (Model-View-Presenter). 
Presenters are common for all of them and they are placed in `common-client` module. 
It contains all business logic and is well tested.
Views are implemented separately for every platform. 
Presenters are using Data Model, and communicating with Views from behind the interfaces that are placed in `common-client` module.
On the other side Presenters are communicating with repositories (network API, databases etc.) 
which are hidden behind an interfaces (for unit-testing purposes) and specified as an expected declarations
which have actial declaration in platform modules (`common-client-jvm` and `common-client-js`).

Application is composing Kotlin [Kotlin Academy](https://blog.kotlin-academy.com/) and presenting them together. 
It also allows giving the feedback this news or to Kotlin Academy. 

Here is the status of planned clients:
* Android - DONE
* Web - DONE ([Demo](https://kotlin-academy.herokuapp.com/))
* Desktop - During designing
* Android Watch - Not yet started
* Firefox plugin - Not yet started
* Chrome plugin - Not yet started
* iOS - Not yet started

We will really appreciate help with all of this areas. 

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

Android client is used to display list of news. It uses common-client logic to display loading, refreshing and obtaining list of elements. Here are some screens:

<img src="art/Android1.png" width="400"> <img src="art/Android2.png" width="400">

## Web

Demo is [here on Heroku](https://kotlin-academy.herokuapp.com/#/).

Web client with the same logic like Android app. It is implemented in React.

## Desktop

Desktop TornadoFx client with the same logic like Android app. During designing. Warning: It is not working with OpenJDK because it doesn't include JavaFX by default. You need to use Oracle JDK.

## Tests

Business logic, especially while it is shared among all the platforms, is unit-tested. Tests are universal, but they are now located [here](https://github.com/MarcinMoskala/KotlinAcademyApp/blob/master/android/app/src/test/java/com/marcinmoskala/kotlinacademy/NewsPresenterUnitTest.kt) in Android module. Although they should be moved to common-client module.

## Heroku

To push on heroku, you need to specify following GRADLE_TASK:

```
heroku config:set GRADLE_TASK="-Dorg.gradle.project.INCLUDE_ANDROID=false :web:build :web:buildBundle copyWeb :backend:build"
```