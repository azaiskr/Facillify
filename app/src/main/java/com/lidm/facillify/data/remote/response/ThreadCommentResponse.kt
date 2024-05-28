package com.lidm.facillify.data.remote.response

data class ThreadCommentResponse(
    val email: String,
    val content: String,
    val time: String
)

data class CreatedThreadCommentResponse(
    val msg: String
)