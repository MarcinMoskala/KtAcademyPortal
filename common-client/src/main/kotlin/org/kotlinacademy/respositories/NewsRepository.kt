package org.kotlinacademy.respositories

import org.kotlinacademy.data.NewsData

interface NewsRepository {

    suspend fun getNewsData(): NewsData
}