package org.kotlinacademy.mobile.view

import com.marcinmoskala.kotlinpreferences.PreferenceHolder

object Prefs : PreferenceHolder() {
    var tokenSentToServer: Boolean by bindToPreferenceField(false, "TOKEN_SENT_TO_SERVER")
}