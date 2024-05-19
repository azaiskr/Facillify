package com.lidm.facillify.data

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: String
)
