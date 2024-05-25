package com.lidm.facillify.data.remote.api

import android.content.Context
import com.lidm.facillify.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        private fun getRetrofit(baseUrl: String, context: Context): Retrofit {
            val loggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

            //TODO: authInterceptor

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