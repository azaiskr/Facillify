package com.lidm.facillify.ui

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lidm.facillify.data.remote.api.ChatbotApiService
import com.lidm.facillify.data.repository.AuthRepository
import com.lidm.facillify.data.repository.ThreadRepository
import com.lidm.facillify.di.Inject
import com.lidm.facillify.ui.viewmodel.AuthViewModel
import com.lidm.facillify.ui.viewmodel.ChatViewModel
import com.lidm.facillify.ui.viewmodel.ProfileViewModel
import com.lidm.facillify.ui.viewmodel.ThreadViewModel

class ViewModelFactory(
    private val apiService: ChatbotApiService,
    private val threadRepository: ThreadRepository,
    private val authRepository: AuthRepository,
) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(authRepository) as T
            }

            modelClass.isAssignableFrom(ChatViewModel::class.java) -> {
                ChatViewModel(apiService) as T
            }

            modelClass.isAssignableFrom(ThreadViewModel::class.java) -> {
                ThreadViewModel(threadRepository) as T
            }

            modelClass.isAssignableFrom( ProfileViewModel::class.java) -> {
                ProfileViewModel(authRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Inject.privodeChatAPiService(context.applicationContext),
                    Inject.provideThreadRepo(context.applicationContext),
                    Inject.provideAuthRepo(context.applicationContext),
                ).also { instance = it }
            }
    }
}