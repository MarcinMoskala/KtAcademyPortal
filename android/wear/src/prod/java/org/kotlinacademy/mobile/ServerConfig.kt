package org.kotlinacademy.mobile

import org.kotlinacademy.respositories.BaseURL
import kotlinx.coroutines.experimental.android.UI as AndroidUI

fun setUpServer(serverCreator: (baseUrl: String)->Unit) {
    val baseUrl = "https://kotlin-academy.herokuapp.com/"
    serverCreator(baseUrl)
}