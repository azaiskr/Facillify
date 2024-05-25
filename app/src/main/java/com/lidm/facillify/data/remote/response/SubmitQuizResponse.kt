package com.lidm.facillify.data.remote.response

import com.google.gson.annotations.SerializedName

data class SubmitQuizResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("quiz_id")
	val quizId: String? = null,

	@field:SerializedName("grade")
	val grade: Number? = null,

	@field:SerializedName("email")
	val email: String? = null
)
