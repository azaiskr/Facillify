package com.lidm.facillify.data.repository

import android.util.Log
import com.lidm.facillify.data.UserPreferences.UserPreferences
import com.lidm.facillify.data.remote.api.ApiService
import com.lidm.facillify.data.remote.request.LoginRequest
import com.lidm.facillify.data.remote.response.ProfileResponse
import com.lidm.facillify.data.remote.response.UserModelResponse
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class UserRepository (
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

    suspend fun getUserProfile(email: String) :Flow<ProfileResponse> {
        return flow {
            try {
                emit(apiService.getUserProfile(email))
            } catch (e:Exception){
                Log.d("User Repository", "getUserProfile: ${e.localizedMessage}" )
                e.printStackTrace()
            }
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
        private var instance:UserRepository? = null
        fun getInstance(apiService: ApiService, userPreferences: UserPreferences): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreferences).also { instance = it }
            }
    }
}