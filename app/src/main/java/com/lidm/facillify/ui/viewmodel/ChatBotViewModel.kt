package com.lidm.facillify.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.ChatMessage
import com.lidm.facillify.data.remote.api.ChatbotApiService
import com.lidm.facillify.data.remote.request.ChatbotRequest
import com.lidm.facillify.util.getCurrentDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class ChatViewModel(private val chatbotApiService: ChatbotApiService) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(listOf(
        ChatMessage(
            text = "Halo, saya FACILLIFY AI. Bagaimana saya bisa membantu Anda hari ini?",
            isUser = false,
            timestamp = getCurrentDateTime()
        )
    ))
    val messages: StateFlow<List<ChatMessage>>
    get() = _messages

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(content: String) {
        val currentTime = getCurrentDateTime()
        _messages.value += ChatMessage(
            text = content,
            isUser = true,
            timestamp = currentTime
        )

        viewModelScope.launch {
            try {
                val systemMessage = com.lidm.facillify.data.remote.response.Message(
                    role = "system",
                    content = "Anda adalah chatbot bernama FACILLIFY AI dari aplikasi Learning Management System (LMS) Faciliffy. Jawab semua pertanyaan dalam bahasa Indonesia."
                )
                val userMessage = com.lidm.facillify.data.remote.response.Message(
                    role = "user",
                    content = content
                )
                val request = ChatbotRequest(
                    model = "gpt-4o",
                    messages = listOf(systemMessage, userMessage)
                )
                val response = chatbotApiService.sendMessage(request)
                val reply = response.choices.firstOrNull()?.message?.content.orEmpty()
                _messages.value += ChatMessage(
                    text = reply,
                    isUser = false,
                    timestamp = getCurrentDateTime()
                )
            } catch (e: Exception) {
                _messages.value += ChatMessage(
                    text = "Error: ${e.message}",
                    isUser = false,
                    timestamp = getCurrentDateTime()
                )
            }
        }
    }
}