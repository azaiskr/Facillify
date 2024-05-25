package com.lidm.facillify.model

data class CreateQuizewewew(
	val questions: List<QuestionsItem?>? = null,
	val time: Int? = null,
	val title: String? = null,
	val descripton: String? = null
)

data class Options(
	val a: String? = null,
	val b: String? = null,
	val c: String? = null,
	val d: String? = null,
	val e: String? = null
)

data class QuestionsItem(
	val correctOptionKey: String? = null,
	val question: String? = null,
	val options: Options? = null
)

