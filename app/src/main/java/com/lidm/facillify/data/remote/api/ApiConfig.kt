package com.lidm.facillify.data.remote.api

import android.content.Context
import android.util.Log
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

            val combinedInterceptor = Interceptor { chain ->
                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder()

                // Add Authorization header if the request is not for the chatbot
                if (!originalRequest.url.toString().contains(BuildConfig.CHATBOT_URL)) {
                    val token = runBlocking {
                        val pref = UserPreferences.getInstance(context)
                        pref.getUserPref().first().token
                    }
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }

                // Add chatbot-specific headers if the request is for the chatbot
                if (originalRequest.url.toString().contains(BuildConfig.CHATBOT_URL)) {
                    requestBuilder
                        .addHeader("Authorization", "Bearer ${BuildConfig.OPENAI_SECRET_KEY}")
                        .addHeader("OpenAI-Organization", BuildConfig.OPENAI_ORGANIZATION)
                        .addHeader("OpenAI-Project", BuildConfig.OPENAI_PROJECT)
                }
                val request = requestBuilder.build()
                chain.proceed(request)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val request = chain.request()
                    Log.d("OkHttp", "Request Headers: ${request.headers}")
                    chain.proceed(request)
                }
                .addInterceptor(combinedInterceptor) // Combining headers carefully
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
            val retrofit = getRetrofit(BuildConfig.CHATBOT_URL, ctx.applicationContext)
            return retrofit.create(ChatbotApiService::class.java)
        }
    }
}
