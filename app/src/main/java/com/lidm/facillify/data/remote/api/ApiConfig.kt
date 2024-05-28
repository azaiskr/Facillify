package com.lidm.facillify.data.remote.api

import android.content.Context
import com.lidm.facillify.BuildConfig
import com.lidm.facillify.data.UserPreferences.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        private fun getRetrofit(baseUrl: String, context: Context): Retrofit {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            //TODO: authInterceptor
            val authInterceptor = Interceptor { chain ->
                val token = runBlocking {
                    val pref = UserPreferences.getInstance(context)
                    pref.getUserPref().first().token
                }
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }

            val chatbotInterceptor = Interceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer ${BuildConfig.SECRET_KEY}")
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chatbotInterceptor)
                .addInterceptor(authInterceptor)
                .build()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        fun getMainApiService(ctx: Context): ApiService {
            val retrofit = getRetrofit(BuildConfig.BASE_URL, ctx)
            return retrofit.create(ApiService::class.java)
        }

        fun getChatbotApiService(ctx: Context): ChatbotApiService {
            val retrofit = getRetrofit(BuildConfig.CHATBOT_URL, ctx)
            return retrofit.create(ChatbotApiService::class.java)
        }

    }
}