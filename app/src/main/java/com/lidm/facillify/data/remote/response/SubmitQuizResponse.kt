package com.lidm.facillify.data.remote.response

import com.google.gson.annotations.SerializedName

data class SubmitQuizResponse(
	val msg: String,
  
	@field:SerializedName("data")
	val data: Data
)

data class Data(

	@field:SerializedName("quiz_id")
	val quizId: String,

	@field:SerializedName("submit_time")
	val submitTime: String,

	@field:SerializedName("num_questions")
	val numQuestions: Int,

	@field:SerializedName("grade")
	val grade: Int,

	@field:SerializedName("correct_answers")
	val correctAnswers: Int,

	@field:SerializedName("student_email")
	val studentEmail: String,

	@field:SerializedName("_id")
	val id: String
)