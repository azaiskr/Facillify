package com.lidm.facillify.data.repository

import com.lidm.facillify.data.UserPreferences.UserPreferences
import com.lidm.facillify.data.remote.api.ApiService
import com.lidm.facillify.data.remote.request.UpdateParentEmailRequest
import com.lidm.facillify.data.remote.response.UserModelResponse
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class SiswaRepository(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {

    suspend fun updateParentEmail (
        studentEmail: String,
        parentEmail: String
    ) = flow {
        emit(ResponseState.Loading)
        try {
            val request = UpdateParentEmailRequest(studentEmail, parentEmail)
            val response = apiService.updateEmail(request)
            emit(ResponseState.Success(response))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    fun getSession(): Flow<UserModelResponse> = userPreferences.getUserPref()

    companion object {
        @Volatile
        private var instance: SiswaRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreferences: UserPreferences
        ): SiswaRepository =
            instance ?: synchronized(this) {
                instance ?: SiswaRepository(apiService, userPreferences).also { instance = it }
            }
    }
}
