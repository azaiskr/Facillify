package com.lidm.facillify.data.repository

import com.lidm.facillify.data.remote.api.ApiService
import com.lidm.facillify.data.remote.request.CreateCommentThreadRequest
import com.lidm.facillify.data.remote.request.CreateThreadRequest
import com.lidm.facillify.data.remote.response.AllThreadResponse
import com.lidm.facillify.data.remote.response.CreatedThreadCommentResponse
import com.lidm.facillify.data.remote.response.ThreadCreatedResponse
import com.lidm.facillify.data.remote.response.ThreadDetailResponse
import retrofit2.HttpException

class ThreadRepository(
    private val apiService: ApiService
) {
    suspend fun createThread(request: CreateThreadRequest): Result<ThreadCreatedResponse> {
        return try {
            val response = apiService.createThread(request)
            Result.success(response)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createThreadComment(request: CreateCommentThreadRequest): Result<CreatedThreadCommentResponse> {
        return try {
            val response = apiService.createThreadComment(request)
            Result.success(response)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAllThreads(): Result<AllThreadResponse> {
        return try {
            val response = apiService.getAllThread()
            Result.success(response)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getThreadDetail(threadId: String): Result<ThreadDetailResponse> {
        return try {
            val response = apiService.getThreadDetail(threadId)
            Result.success(response)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    companion object {
        private var instance: ThreadRepository? = null
        fun getInstance(apiService: ApiService): ThreadRepository =
            instance ?: synchronized(this) {
                instance ?: ThreadRepository(apiService).also { instance = it }
            }
    }
}