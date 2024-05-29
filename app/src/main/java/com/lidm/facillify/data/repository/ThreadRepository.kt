package com.lidm.facillify.data.repository

import com.lidm.facillify.data.UserPreferences.UserPreferences
import com.lidm.facillify.data.remote.api.ApiService
import com.lidm.facillify.data.remote.request.CreateCommentThreadRequest
import com.lidm.facillify.data.remote.request.CreateThreadRequest
import com.lidm.facillify.data.remote.response.CreatedThreadCommentResponse
import com.lidm.facillify.data.remote.response.ThreadCreatedResponse
import com.lidm.facillify.data.remote.response.ThreadDetailResponse
import com.lidm.facillify.data.remote.response.ThreadResponse
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class ThreadRepository(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences,) {
    suspend fun createThread(request: CreateThreadRequest): Flow<ResponseState<ThreadCreatedResponse>> = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.createThread(request)
            emit(ResponseState.Success(response))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.message()))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    suspend fun createThreadComment(request: CreateCommentThreadRequest): Flow<ResponseState<CreatedThreadCommentResponse>> = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.createThreadComment(request)
            emit(ResponseState.Success(response))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.message()))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    suspend fun getAllThreads(): Flow<ResponseState<List<ThreadResponse>>> = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.getAllThread()
            emit(ResponseState.Success(response.result))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.message()))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    suspend fun getThreadDetail(threadId: String): Flow<ResponseState<ThreadDetailResponse>> = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.getThreadDetail(threadId)
            emit(ResponseState.Success(response))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.message()))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    companion object {
        private var instance:ThreadRepository? = null
        fun getInstance(apiService: ApiService, userPreferences: UserPreferences): ThreadRepository =
            instance ?: synchronized(this) {
                instance ?: ThreadRepository(apiService, userPreferences).also { instance = it }
            }
    }
}