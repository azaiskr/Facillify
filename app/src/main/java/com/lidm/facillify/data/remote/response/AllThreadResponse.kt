package com.lidm.facillify.data.remote.response

data class AllThreadResponse(
    val msg: String,
    val result: List<ThreadResponse>
)
data class ThreadResponse(
    val _id: String,
    val op_email: String,
    val title: String,
    val content: String,
    val subject: String,
    val time: String,
    val op_name: String
)

data class ThreadDetailResponse(
    val _id: String,
    val title: String,
    val op_email: String,
    val content: String,
    val comments: List<ThreadCommentResponse>,
    val op_name: String
)

data class ThreadCreatedResponse(
    val msg: String,
    val created_id: String
)