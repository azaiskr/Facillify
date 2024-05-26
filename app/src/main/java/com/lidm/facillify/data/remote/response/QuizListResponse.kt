package com.lidm.facillify.data.remote.response

import com.google.gson.annotations.SerializedName

data class QuizListResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("result")
	val result: List<QuizItem?>? = null
)

data class QuizItem(

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
