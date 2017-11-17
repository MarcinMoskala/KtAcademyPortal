package com.marcinmoskala.kotlinacademy.respositories

actual object RepositoriesProvider {
    actual fun getNewsRepository(): NewsRepository = NewsRepositoryImpl()
}