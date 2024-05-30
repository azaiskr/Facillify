package com.lidm.facillify.data.repository

import android.util.Log
import com.lidm.facillify.data.UserPreferences.UserPreferences
import com.lidm.facillify.data.remote.api.ApiService
import com.lidm.facillify.data.remote.request.CreateAssessmentForSiswaRequest
import com.lidm.facillify.data.remote.request.LoginRequest
import com.lidm.facillify.data.remote.request.RegisterRequest
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

    suspend fun registerMurid(
        type: String,
        email: String,
        name: String,
        password: String,
        pod: String,
        dob: String,
        gender: String,
        address: String,
        phone_number: String,
        religion: String,
        nisn: String,
    ) = flow {
        emit(ResponseState.Loading)
        try {
            val request = RegisterRequest(
                type = type,
                email = email,
                password = password,
                name = name,
                pob = pod,
                dob = dob,
                gender = gender,
                address = address,
                phone_number = phone_number,
                religion = religion,
                nisn = nisn
            )
            val response = apiService.registerUser(request)
            emit(ResponseState.Success(response))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    suspend fun registerGuru(
        type: String,
        email: String,
        password: String,
        name: String,
        pod: String,
        dob: String,
        gender: String,
        address: String,
        phone_number: String,
        religion: String,
        nip: String,
    ) = flow {
        emit(ResponseState.Loading)
        try {
            val request = RegisterRequest(
                type = type,
                email = email,
                password = password,
                name = name,
                pob = pod,
                dob = dob,
                gender = gender,
                address = address,
                phone_number = phone_number,
                religion = religion,
                nip = nip
            )
            val response = apiService.registerUser(request)
            emit(ResponseState.Success(response))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
    suspend fun registerOrtu(
        type: String,
        email: String,
        password: String,
        name: String,
        pod: String,
        dob: String,
        gender: String,
        address: String,
        phone_number: String,
        religion: String,
        job: String,
    ) = flow {
        emit(ResponseState.Loading)
        try {
            val request = RegisterRequest(
                type = type,
                email = email,
                password = password,
                name = name,
                pob = pod,
                dob = dob,
                gender = gender,
                address = address,
                phone_number = phone_number,
                religion = religion,
                job = job
            )
            val response = apiService.registerUser(request)
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

    //ASSESSMENT FOR TEACHER ONLY
    suspend fun createAssessment(request: CreateAssessmentForSiswaRequest) = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.createAssesmentForSiswa(request)
            emit(ResponseState.Success(response))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    companion object {
        private var instance:UserRepository? = null
        fun getInstance(apiService: ApiService, userPreferences: UserPreferences): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreferences).also { instance = it }
            }
    }
}