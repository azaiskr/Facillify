package com.lidm.facillify.data.repository

import com.lidm.facillify.data.UserPreferences.UserPreferences
import com.lidm.facillify.data.remote.api.ApiService
import com.lidm.facillify.data.remote.request.LoginRequest
import com.lidm.facillify.data.remote.response.UserModelResponse
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class AuthRepository (
    private val apiService: ApiService,
    private val userPreferences: UserPreferences,
) {

    suspend fun loginUser(
        email: String,
        password: String
    ) = flow {
        emit(ResponseState.Loading)
        try{
            val request = LoginRequest(email, password)
            val response = apiService.loginUser(request)
            emit(ResponseState.Success(response))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    suspend fun saveSession(user: UserModelResponse) {
        userPreferences.saveUserPref(user)
    }

    suspend fun clearSession() {
        userPreferences.clearUserPref()
    }

    fun getSession(): Flow<UserModelResponse> = userPreferences.getUserPref()

    companion object {
        private var instance:AuthRepository? = null
        fun getInstance(apiService: ApiService, userPreferences: UserPreferences): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService, userPreferences).also { instance = it }
            }
    }
}