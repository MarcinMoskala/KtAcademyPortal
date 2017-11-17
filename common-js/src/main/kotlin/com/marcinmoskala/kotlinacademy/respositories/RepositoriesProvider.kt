package com.marcinmoskala.kotlinacademy.respositories

actual object RepositoriesProvider {

    private val newsRepository by lazy { NewsRepositoryImpl() }

    actual fun getNewsRepository(): NewsRepository = newsRepository
}