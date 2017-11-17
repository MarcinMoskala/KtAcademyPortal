package com.marcinmoskala.kotlinacademy.respositories

expect object RepositoriesProvider {

    fun getNewsRepository(): NewsRepository
}