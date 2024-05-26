package com.lidm.facillify.data.remote.request

data class CreateQuizRequest(
    val title: String,
    val description: String,
    val questions: List<Question>,
    val time: Int
)

data class Question(
    val question: String,
    val options: List<Option>,
    val correct_option_key: String
)

data class Option(
    val a: String? = null,
    val b: String? = null,
    val c: String? = null,
    val d: String? = null,
    val e: String? = null,
)

