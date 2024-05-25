package com.lidm.facillify.data.remote.response

import com.google.gson.annotations.SerializedName

data class QuizDetailResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("result")
	val result: Quiz? = null
)

data class Quiz(

	@field:SerializedName("questions")
	val questions: List<QuestionsItem?>? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("time")
	val time: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("descripton")
	val descripton: String? = null
)

data class QuestionsItem(

	@field:SerializedName("correct_option_key")
	val correctOptionKey: String? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("options")
	val options: List<OptionsItem?>? = null
)

data class OptionsItem(

	@field:SerializedName("a")
	val a: String? = null,

	@field:SerializedName("b")
	val b: String? = null,

	@field:SerializedName("c")
	val c: String? = null,

	@field:SerializedName("d")
	val d: String? = null,

	@field:SerializedName("e")
	val e: String? = null
)
