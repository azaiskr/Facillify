package com.lidm.facillify.data.remote.response

import com.google.gson.annotations.SerializedName

data class QuizDetailResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("result")
	val result: Quiz
)

data class Quiz(

	@field:SerializedName("questions")
	val questions: List<QuestionsItem>,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("time")
	val time: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("descripton")
	val descripton: String,
)

data class QuestionsItem(

	@field:SerializedName("correct_option_key")
	val correctOptionKey: String,

	@field:SerializedName("question")
	val question: String,

	@field:SerializedName("options")
	val options: List<String>
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
