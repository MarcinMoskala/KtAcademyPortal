package org.kotlinacademy.mobile

import kotlinx.coroutines.experimental.android.UI as AndroidUI

fun setUpServer(serverCreator: (baseUrl: String)->Unit) {
    val baseUrl = "http://10.0.2.2:8080/"
    serverCreator(baseUrl)
}
