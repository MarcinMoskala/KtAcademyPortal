package academy.kot.portal.mobile.view

import com.marcinmoskala.kotlinpreferences.PreferenceHolder

object Prefs : PreferenceHolder() {
    var tokenSentToServer: String? by bindToPreferenceFieldNullable("FIREBASE_TOKEN")
}