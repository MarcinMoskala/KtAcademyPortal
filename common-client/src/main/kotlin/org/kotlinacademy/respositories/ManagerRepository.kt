package org.kotlinacademy.respositories

import org.kotlinacademy.data.NewsData

interface ManagerRepository {

    suspend fun getPropositions(secret: String): NewsData
    suspend fun acceptInfo(id: Int, secret: String)
    suspend fun acceptPuzzler(id: Int, secret: String)
    suspend fun acceptImportantPuzzler(id: Int, secret: String)
    suspend fun puzzlerToTop(id: Int, secret: String)
    suspend fun rejectInfo(id: Int, secret: String)
    suspend fun rejectPuzzler(id: Int, secret: String)
}