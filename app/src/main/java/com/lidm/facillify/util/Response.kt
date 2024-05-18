package com.lidm.facillify.util

sealed class Response<out T:Any?> {
    data object Loading : Response<Nothing>()
    data class Success<out T:Any?>(val data: T) : Response<T>()
    data class Error(val error: String) : Response<Nothing>()
}