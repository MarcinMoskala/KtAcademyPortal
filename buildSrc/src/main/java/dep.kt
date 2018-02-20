object Deps {

  val versions = Versions

  const val kotlin_stdlib_common = "org.jetbrains.kotlin:kotlin-stdlib-common:" + versions.kotlin
  const val kotlin_stdlib_jre7 = "org.jetbrains.kotlin:kotlin-stdlib-jre7:" + versions.kotlin
  const val kotlin_stdlib_jre8 = "org.jetbrains.kotlin:kotlin-stdlib-jre8:" + versions.kotlin
  const val kotlin_stdlib_js = "org.jetbrains.kotlin:kotlin-stdlib-js:" + versions.kotlin
  const val serialization_common =
    "org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:" + versions.serialization
  const val coroutines_common = "org.jetbrains.kotlinx:kotlinx-coroutines-core:" + versions.coroutines
  val kotlin_test_common = arrayOf(
    "org.jetbrains.kotlin:kotlin-test-common:" + versions.kotlin,
    "org.jetbrains.kotlin:kotlin-test-annotations-common:" + versions.kotlin
  )

  // JS
  val kotlin_test_js = arrayOf(
    "org.jetbrains.kotlin:kotlin-test-js:" + versions.kotlin,
    "org.jetbrains.kotlin:kotlin-test:" + versions.kotlin
  )
  const val coroutines_js = "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:" + versions.coroutines
  const val kotlin_serializatoin_js = "org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:" + versions.serialization
  const val kotlinx_html = "org.jetbrains.kotlinx:kotlinx-html-js:" + versions.kotlinx_html

  val kotlin_react = arrayOf(
    "org.jetbrains:kotlin-react:" + versions.react,
    "org.jetbrains:kotlin-react-dom:" + versions.react_dom
  )

  // JVM
  val retrofit = arrayOf(
    "com.squareup.retrofit2:retrofit:" + versions.retrofit,
    "com.squareup.retrofit2:converter-gson:" + versions.retrofit,
    "com.squareup.retrofit2:converter-scalars:" + versions.retrofit,
    "com.squareup.okhttp3:okhttp:" + versions.okhttp,
    "com.squareup.okhttp3:logging-interceptor:" + versions.okhttp,
    "ru.gildor.coroutines:kotlin-coroutines-retrofit:" + versions.coroutines_retrofit
  )

  const val gson = "com.google.code.gson:gson:" + versions.gson
  const val junit = "junit:junit:" + versions.junit

  const val mockk = "io.mockk:mockk:1.7.2"

  val kotlin_junit = arrayOf(
    "org.jetbrains.kotlin:kotlin-test:" + versions.kotlin,
    "org.jetbrains.kotlin:kotlin-test-junit:" + versions.kotlin
  )
  const val kotlin_serialization_jvm = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:" + versions.serialization

  // Backend
  const val sendgrid = "com.sendgrid:sendgrid-java:" + versions.sendgrid
  const val ktor_netty = "io.ktor:ktor-server-netty:" + versions.ktor
  const val ktor_gson = "io.ktor:ktor-gson:" + versions.ktor

  val ktor_squash_postgres_and_h2 = arrayOf(
    "org.jetbrains.squash:squash:" + versions.squash,
    "org.jetbrains.squash:squash-postgres:" + versions.squash,
    "org.postgresql:postgresql:" + versions.postgresql,
    "com.zaxxer:HikariCP:" + versions.hikari,
    "org.jetbrains.squash:squash-h2:" + versions.squash
  )
  const val logback = "ch.qos.logback:logback-classic:" + versions.logback

  // Android
  val firebase = arrayOf(
    "com.google.firebase:firebase-core:" + versions.firebase,
    "com.google.firebase:firebase-messaging:" + versions.firebase
  )
  const val glide = "com.github.bumptech.glide:glide:" + versions.glide
  const val anko_coroutines = "org.jetbrains.anko:anko-coroutines:" + versions.anko
  val activitystarter_kotlin = arrayOf(
    "com.marcinmoskala.activitystarter:activitystarter:" + versions.activitystarter,
    "com.marcinmoskala.activitystarter:activitystarter-kotlin:" + versions.activitystarter
  )
  const val activitystarter_compiler =
    "com.marcinmoskala.activitystarter:activitystarter-compiler:" + versions.activitystarter
  const val kotlinandroidviewbindings =
    "com.github.MarcinMoskala:KotlinAndroidViewBindings:" + versions.kotlinandroidviewbindings
  val android_support = arrayOf(
    "com.android.support:design:" + versions.android,
    "com.android.support:appcompat-v7:" + versions.android,
    "com.android.support:recyclerview-v7:" + versions.android
  )
  const val espresso = "com.android.support.test.espresso:espresso-core:" + versions.espresso
  val wear =
    arrayOf("com.google.android.support:wearable:" + versions.wearable, "com.android.support:wear:" + versions.android)
  val kotlin_preferences =
    arrayOf("com.marcinmoskala.PreferenceHolder:preferenceholder:" + versions.kotlin_preferences)

  // Desktop
  const val tornadofx = "no.tornado:tornadofx:" + versions.tornadofx
  const val controlsfx = "org.controlsfx:controlsfx:" + versions.controlsfx
  const val kotlin_coroutines_javafx = "org.jetbrains.kotlinx:kotlinx-coroutines-javafx:" + versions.javafx
  const val tray_notifications = "com.github.PlusHaze:TrayNotification:" + versions.tray_notification
}