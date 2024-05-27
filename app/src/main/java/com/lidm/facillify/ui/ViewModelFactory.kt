package com.lidm.facillify.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lidm.facillify.data.remote.api.ChatbotApiService
import com.lidm.facillify.ui.viewmodel.ChatViewModel

class ViewModelFactory(private val apiService: ChatbotApiService) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}