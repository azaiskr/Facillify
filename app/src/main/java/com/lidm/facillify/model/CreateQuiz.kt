package com.lidm.facillify.model

data class CreateQuiz(
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
    val option: String,
)