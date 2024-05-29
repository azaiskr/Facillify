package com.lidm.facillify.data.remote.response

data class UpdateEmailParentResponse(
	val msg: String,
	val result: Result?
)

data class Result(
	val parentEmail: String,
	val studentEmail: String
)

