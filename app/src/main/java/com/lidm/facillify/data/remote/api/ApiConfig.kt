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

            val authInterceptor = Interceptor { chain ->
                val token = runBlocking {
                    val pref = UserPreferences.getInstance(context)
                    pref.getUserPref().first().token
                }
                Log.d("AuthInterceptor", "Token: $token")
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                Log.d("AuthInterceptor", "Request Headers: ${request.headers}")
                chain.proceed(request)
            }
            val chatbotInterceptor = Interceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer ${BuildConfig.SECRET_KEY}")
                val request = requestBuilder.build()
                Log.d("ChatbotInterceptor", "Token: ${BuildConfig.SECRET_KEY}")
                chain.proceed(request)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor) // Combining headers carefully
//                .addInterceptor(chatbotInterceptor)
                .addInterceptor(loggingInterceptor)
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
