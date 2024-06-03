package com.lidm.facillify.data.remote.response

data class GradeHistoryResponse(
    val msg: String,
    val result: List<GradeHistory>
)

data class GradeHistory(
    val _id: String,
    val submit_time: String,
    val quiz_id: String,
    val student_email: String,
    val num_questions: Int,
    val correct_answers: Int,
    val grade: Double,
    val quiz_title: String
)