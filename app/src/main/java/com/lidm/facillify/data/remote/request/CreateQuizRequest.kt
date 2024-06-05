package com.lidm.facillify.data.remote.request

data class CreateQuizRequest(
    val title: String,
    val description: String,
    val questions: List<Question>,
    val time: Int
)

data class Question(
    val question: String,
    val options: List<String>,
    val correct_option_key: String
)

data class Options(
    val a: String,
    val b: String,
    val c: String,
    val d: String,
    val e: String,
)

