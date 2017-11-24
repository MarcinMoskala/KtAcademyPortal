package com.marcinmoskala.kotlinacademy.components

external interface  BaseState: react.RState {
    var error: Throwable?
}