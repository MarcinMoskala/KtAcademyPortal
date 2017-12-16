package org.kotlinacademy.mobile

import org.kotlinacademy.respositories.BaseURL
import kotlinx.coroutines.experimental.android.UI as AndroidUI

class ServerConfig {
    fun setUp() {
        BaseURL = "https://kotlin-academy.herokuapp.com/"
    }
}