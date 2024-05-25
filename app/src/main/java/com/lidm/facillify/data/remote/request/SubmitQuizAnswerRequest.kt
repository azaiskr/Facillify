package com.lidm.facillify.data.remote.request

data class SubmitQuizAnswerRequest(
    val email: String,
    val answers: List<String>
)
