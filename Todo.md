## TODO

There is a lot in this repository to be done. Here is current list of tasks:

**Testing:**

- [ ] All `common-client` presenters and use cases should be tested
- [ ] `common-client` tests should be moved from [`common-android`](https://github.com/MarcinMoskala/KotlinAcademyApp/tree/master/android/common/src/test/java/org/kotlinacademy) to `common-client` test sources (check out [this](https://www.youtube.com/watch?v=ZbANCuZ_qqw&list=PLQ176FUIyIUY6UK1cgVsbdPYA3X5WLam5&index=6))
- [ ] All `backend` use cases should be tested
- [ ] Android `mobile` should have instrumentation tests (override repositories to make tests independent on API)
- [ ] Android `mobile` should have development version that allows to test offline (override `App` and set fixed values to repositories)
- [ ] Android `wear` should have instrumentation tests
- [ ] `web` should have instrumentation tests

**Design:**

- [ ] New general design is needed that is using open-source images. Current lantern can be used but this image does not look so good for wide banner size.

**Backend:**

- [ ] Notifications should be customized in every platform
- [ ] When started locally it is not always reliable and it needs to be restarted (it never happen on Heroku)

**Deskop:**

- [ ] New design should be applied
- [ ] There should be mechanism for building executable (check out [this](https://github.com/edvin/fxldemo-gradle))

**Web:**

- [ ] Notification push system should be moved from [static js file](https://github.com/MarcinMoskala/KotlinAcademyApp/blob/master/web/src/main/web/js/initFirebase.js) to Kotlin React logic
- [ ] Fix `run` task to correctly build and start module
- [ ] `npm run serve` is not reliable and not always starts correctly
- [ ] Banner needs better behavior

**Gradle:**

- [ ] There should be single gradle task for heroku build
- [ ] There should be single gradle task for running all tests
- [ ] There should be some CI connected that is running all tests (like Travis or CircleCI)
- [ ] Clean-up in dependencies and versions

**iOS:**

- [ ] There should be single iOS project view implemented
- [ ] iOS should use presenters from `common-client`
- [ ] iOS module should be build with Gradle

**Other clients:**

- [ ] Make Firefox Plugin (check out [this](https://medium.com/@Cypressious/your-first-firefox-web-extension-in-kotlin-348fc907915) and [this](https://medium.com/@Cypressious/your-second-firefox-extension-in-kotlin-bafd91d87c41), and use [this](https://github.com/cypressious/kotlin-webextensions-declarations))
- [ ] Make Chrome Plugin