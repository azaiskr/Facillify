package com.lidm.facillify.data.remote.request

data class ChatbotRequest(
    val model: String,
    val messages: List<Message>
)

data class Message(
    val role: String,
    val content: String
)
