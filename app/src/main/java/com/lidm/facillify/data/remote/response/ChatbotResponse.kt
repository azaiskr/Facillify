package com.lidm.facillify.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChatbotResponse(

	@field:SerializedName("created")
	val created: Int,

	@field:SerializedName("usage")
	val usage: Usage,

	@field:SerializedName("model")
	val model: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("choices")
	val choices: List<ChoicesItem>,

	@field:SerializedName("object")
	val objectModel: String
)

data class Usage(

	@field:SerializedName("completion_tokens")
	val completionTokens: Int,

	@field:SerializedName("prompt_tokens")
	val promptTokens: Int,

	@field:SerializedName("total_tokens")
	val totalTokens: Int
)

data class Message(

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("content")
	val content: String
)

data class ChoicesItem(

	@field:SerializedName("finish_reason")
	val finishReason: String,

	@field:SerializedName("index")
	val index: Int,

	@field:SerializedName("message")
	val message: Message
)
