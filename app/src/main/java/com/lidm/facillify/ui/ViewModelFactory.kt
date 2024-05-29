package com.lidm.facillify.ui

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lidm.facillify.data.remote.api.ChatbotApiService
import com.lidm.facillify.data.repository.UserRepository
import com.lidm.facillify.data.repository.ThreadRepository
import com.lidm.facillify.di.Inject
import com.lidm.facillify.ui.viewmodel.AuthViewModel
import com.lidm.facillify.ui.viewmodel.ChatViewModel
import com.lidm.facillify.ui.viewmodel.LatihanSiswaViewModel
import com.lidm.facillify.ui.viewmodel.ProfileViewModel
import com.lidm.facillify.ui.viewmodel.ThreadViewModel

class ViewModelFactory(
    private val repositories: Map<Class<*>, Any>
) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                val userRepository = repositories[UserRepository::class.java] as UserRepository
                AuthViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                val userRepository = repositories[UserRepository::class.java] as UserRepository
                MainViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(ChatViewModel::class.java) -> {
                val chatbotApiService = repositories[ChatbotApiService::class.java] as ChatbotApiService
                ChatViewModel(chatbotApiService) as T
            }
            modelClass.isAssignableFrom(ThreadViewModel::class.java) -> {
                val threadRepository = repositories[ThreadRepository::class.java] as ThreadRepository
                ThreadViewModel(threadRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                val userRepository = repositories[UserRepository::class.java] as UserRepository
                ProfileViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(LatihanSiswaViewModel::class.java) -> {
                LatihanSiswaViewModel() as T
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
                    mapOf(
                        UserRepository::class.java to Inject.provideAuthRepo(context.applicationContext),
                        ChatbotApiService::class.java to Inject.privodeChatAPiService(context.applicationContext),
                        ThreadRepository::class.java to Inject.provideThreadRepo(context.applicationContext)

                        // Tambahkan repository lainnya di sini
                    )
                ).also { instance = it }
            }
    }
}