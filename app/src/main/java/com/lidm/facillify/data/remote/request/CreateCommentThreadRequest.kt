package com.lidm.facillify.data.remote.request

data class CreateCommentThreadRequest(
    val thread_id: String,
    val email: String,
    val content: String
)