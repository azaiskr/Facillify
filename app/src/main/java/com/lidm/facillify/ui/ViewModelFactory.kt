package com.lidm.facillify.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lidm.facillify.data.remote.api.ChatbotApiService
import com.lidm.facillify.data.repository.ThreadRepository
import com.lidm.facillify.ui.viewmodel.ChatViewModel
import com.lidm.facillify.ui.viewmodel.ThreadViewModel

class ViewModelFactory(
    private val apiService: ChatbotApiService,
    private val threadRepository: ThreadRepository) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ChatViewModel::class.java) -> {
                ChatViewModel(apiService) as T
            }
            modelClass.isAssignableFrom(ThreadViewModel::class.java) -> {
                ThreadViewModel(threadRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}