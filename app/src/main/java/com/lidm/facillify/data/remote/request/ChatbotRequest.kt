package com.lidm.facillify.data.remote.request

import com.lidm.facillify.data.remote.response.Message

data class ChatbotRequest(
    val model: String,
    val messages: List<Message>
)

data class Message(
    val role: String,
    val content: String
)
