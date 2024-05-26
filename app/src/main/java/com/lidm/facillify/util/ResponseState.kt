package com.lidm.facillify.util

sealed class ResponseState<out T:Any?> {
    data object Loading : ResponseState<Nothing>()
    data class Success<out T:Any?>(val data: T) : ResponseState<T>()
    data class Error(val error: String) : ResponseState<Nothing>()
}