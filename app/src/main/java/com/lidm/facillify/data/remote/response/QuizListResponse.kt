package com.lidm.facillify.data.remote.response

import com.google.gson.annotations.SerializedName

data class QuizListResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("result")
	val result: List<QuizListItem>
)

data class QuizListItem(

	@field:SerializedName("num_questions")
	val numQuestions: Int? = 0,

	@field:SerializedName("description")
	val description: String? = "",

	@field:SerializedName("_id")
	val id: String = "",

	@field:SerializedName("time")
	val time: Int? = 0,

	@field:SerializedName("title")
	val title: String = "",
)
