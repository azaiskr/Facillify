package com.lidm.facillify.data.remote.response

import com.google.gson.annotations.SerializedName

data class SubmitQuizResponse(
	val msg: String,
	val data: DataQuiz
)

data class DataQuiz(
	val submit_time: String,
	val quiz_id: String,
	val student_email: String,
	val num_questions: Int,
	val correct_answers: Int,
	val grade: Double,
	val _id: String
)