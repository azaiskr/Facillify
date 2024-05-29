package com.lidm.facillify.data.remote.response

data class UserModelResponse(
    val msg: String,
    val email: String,
    val token: String,
    val type: String,
)