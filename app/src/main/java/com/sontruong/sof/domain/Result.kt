package com.sontruong.sof.domain

import java.lang.Exception

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading: Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Succes[data=$data]"
            is Error -> "Error[exception=$exception]"
            is Loading -> "Loading"
        }
    }
}