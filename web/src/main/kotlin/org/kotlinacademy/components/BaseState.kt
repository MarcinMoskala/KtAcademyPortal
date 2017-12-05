package org.kotlinacademy.components

external interface  BaseState: react.RState {
    var error: Throwable?
}