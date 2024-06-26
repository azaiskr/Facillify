package com.lidm.facillify.data.repository

import android.util.Log
import com.lidm.facillify.data.UserPreferences.UserPreferences
import com.lidm.facillify.data.local.paketMateri.materi_bangun_ruang
import com.lidm.facillify.data.remote.api.ApiService
import com.lidm.facillify.data.remote.request.CreateAssessmentForSiswaRequest
import com.lidm.facillify.data.remote.request.LearningStyleRequest
import com.lidm.facillify.data.remote.request.SubmitQuizAnswerRequest
import com.lidm.facillify.data.remote.request.UpdateParentEmailRequest
import com.lidm.facillify.data.remote.response.DetailAssesment
import com.lidm.facillify.data.remote.response.GradeHistory
import com.lidm.facillify.data.remote.response.UserModelResponse
import com.lidm.facillify.data.remote.response.UserProfile
import com.lidm.facillify.data.remote.response.convertMaterialListToMateriBelajar
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException

class SiswaRepository(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {

    suspend fun updateParentEmail(
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

    //GET EMAIL USER
    suspend fun getEmailUser(): Flow<ResponseState<String>> = flow {
        emit(ResponseState.Loading)
        try {
            val response = userPreferences.getUserPref().first().email
            Log.d("SiswaRepository", "getEmailUser: $response")
            emit(ResponseState.Success(response))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.message()))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    //ASSESSMENT

    suspend fun getAssessment(email: String): Flow<ResponseState<DetailAssesment>> = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.getSiswaAssement(email)
            emit(ResponseState.Success(response.result))
        } catch (e: HttpException) {
            if (e.code() == 404) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = errorBody?.let { JSONObject(it).getString("msg") }
                val blankDetailAssesment: DetailAssesment = DetailAssesment(
                    _id = "",
                    email = "",
                    evaluation = "Belum ada evaluasi dari Guru",
                    suggestion = "Belum ada saran dari Guru",
                    time = ""
                )
                if (errorMessage == "Student not found") {
                    emit(ResponseState.Success(blankDetailAssesment))
                } else {
                    emit(ResponseState.Error("HTTP ${e.code()}: $errorMessage"))
                }
            } else {
                emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }

    }

    //GET PROFILE DATA
    suspend fun getProfileData(
        email: String
    ): Flow<ResponseState<UserProfile>> = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.getUserProfile(email)
            emit(ResponseState.Success(response.result))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.message()))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    //GET HISTORY STUDENT
    suspend fun getHistoryStudent(email: String): Flow<ResponseState<List<GradeHistory>>> = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.getSiswaGrade(email)
            emit(ResponseState.Success(response.result))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    //QUIZ
    suspend fun getQuizList() = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.getQuizList()
            emit(ResponseState.Success(response))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    suspend fun getQuizDetail(id: String) = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.getQuiz(id)
            emit(ResponseState.Success(response))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    suspend fun submitQuiz(quizId: String, request: SubmitQuizAnswerRequest) = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.submitQuizAnswer(quizId, request)
            emit(ResponseState.Success(response))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
    suspend fun postLearningStyle(request: LearningStyleRequest) = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.putLearningStyle(request)
            emit(ResponseState.Success(response))
                    } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
    // MATERIALS
    suspend fun getCombinedMaterialList() = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.getMaterialList()
            val materialListFromApi = response.result?.mapIndexed { index, material ->
                convertMaterialListToMateriBelajar(material, (index + 1).toString())
            }
            val localMaterialList = listOf(materi_bangun_ruang)
            if (materialListFromApi != null) {
                emit(ResponseState.Success(materialListFromApi + localMaterialList))
            } else {
                emit(ResponseState.Success(localMaterialList))
            }
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    suspend fun getMaterialDetail(id: String) = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.getMaterialList()
            val materialListFromApi = response.result?.mapIndexed { index, material ->
                convertMaterialListToMateriBelajar(material, (index + 1).toString())
            }
            val localMaterialList = listOf(materi_bangun_ruang)
            val materiDetail = (materialListFromApi?.plus(localMaterialList))?.find { it.id == id }

            emit(ResponseState.Success(materiDetail))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    suspend fun getVideoContent(idMateri: String, idVideo: String) = flow {
        emit(ResponseState.Loading)
        try {
            // val apiResponse = apiService.getVideoContent(id)
            val localResponse = listOf(materi_bangun_ruang).find { it.id == idMateri }
            val videoItem = localResponse?.materiVideo?.find { it.id == idVideo }
            emit(ResponseState.Success(videoItem))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
            Log.d("SiswaRepository", "getVideoContent: ${e.localizedMessage}")
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
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
