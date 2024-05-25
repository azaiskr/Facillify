package com.lidm.facillify.data.remote.api

import com.lidm.facillify.data.remote.request.ChatbotRequest
import com.lidm.facillify.data.remote.response.ChatbotResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatbotApiService {

    //TODO: double check, yet not sure
    @POST("/v1/chat/completions")
    suspend fun sendMessage(
        @Body message: ChatbotRequest
    ) : ChatbotResponse

}