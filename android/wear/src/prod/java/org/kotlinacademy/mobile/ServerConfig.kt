package org.kotlinacademy.mobile

import org.kotlinacademy.respositories.BaseURL
import kotlinx.coroutines.experimental.android.UI as AndroidUI

fun setUpServer() {
    BaseURL = "https://kotlin-academy.herokuapp.com/"
}