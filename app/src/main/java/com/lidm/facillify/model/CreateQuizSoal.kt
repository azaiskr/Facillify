package com.lidm.facillify.model

data class CreateQuizSoal(
    val descripton: String,
    val questions: List<Question>,
    val time: Int,
    val title: String
)