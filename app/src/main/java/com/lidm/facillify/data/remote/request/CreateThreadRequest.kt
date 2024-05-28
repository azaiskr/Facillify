package com.lidm.facillify.data.remote.request

data class CreateThreadRequest(
    val op_email: String,
    val title: String,
    val content: String,
    val subject: String
)