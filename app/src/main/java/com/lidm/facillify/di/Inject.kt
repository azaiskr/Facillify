package com.lidm.facillify.di

import android.content.Context
import com.lidm.facillify.data.UserPreferences.UserPreferences
import com.lidm.facillify.data.remote.api.ApiConfig
import com.lidm.facillify.data.remote.api.ChatbotApiService
import com.lidm.facillify.data.repository.UserRepository
import com.lidm.facillify.data.repository.ThreadRepository

object Inject {
    fun provideThreadRepo(context: Context): ThreadRepository {
     return ThreadRepository.getInstance(ApiConfig.getMainApiService(context))
    }

    fun privodeChatAPiService(context: Context) : ChatbotApiService {
        return ApiConfig.getChatbotApiService(context)
    }

    fun provideAuthRepo(context: Context): UserRepository {
        val apiService = ApiConfig.getMainApiService(context.applicationContext)
        val userPref = UserPreferences.getInstance(context.applicationContext)
        return UserRepository.getInstance(apiService, userPref)
    }
}