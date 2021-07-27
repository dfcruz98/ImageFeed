package com.example.imagefeed.utils

sealed class ActionResult<out R> {
    data class Success<out R>(val data: R) : ActionResult<R>()
    data class Error<out R>(val message: String) : ActionResult<R>()
}