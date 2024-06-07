package com.lidm.facillify.data.repository

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.lidm.facillify.data.UserPreferences.UserPreferences
import com.lidm.facillify.data.remote.api.ApiService
import com.lidm.facillify.data.remote.request.CreateAssessmentForSiswaRequest
import com.lidm.facillify.data.remote.request.CreateQuizRequest
import com.lidm.facillify.data.remote.request.LoginRequest
import com.lidm.facillify.data.remote.request.MaterialRequest
import com.lidm.facillify.data.remote.request.RegisterRequest
import com.lidm.facillify.data.remote.response.GetStudentResponse
import com.lidm.facillify.data.remote.response.ProfileResponse
import com.lidm.facillify.data.remote.response.SubmitQuizResponse
import com.lidm.facillify.data.remote.response.UpdateImageResponse
import com.lidm.facillify.data.remote.response.UserModelResponse
import com.lidm.facillify.util.ResponseState
import com.lidm.facillify.util.createPartFromString
import com.lidm.facillify.util.getFilePartFromUri
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream


class UserRepository (
    private val apiService: ApiService,
    private val userPreferences: UserPreferences,
    private val context: Context
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
    suspend fun getUserProfile(email: String) :Flow<ResponseState<ProfileResponse>> {
        return flow {
            try {
                val response = apiService.getUserProfile(email)
                emit(ResponseState.Success(response))
            } catch (e: HttpException){
                emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
            catch (e:Exception){
                emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
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

    //GET ALL STUDENT FOR TEACHER ONLY
    suspend fun getAllStudent(): Flow<ResponseState<List<GetStudentResponse>>> = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.getAllStudent()
            emit(ResponseState.Success(response.result))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    //UPDATE PHOTO PROFILE
    suspend fun uploadNewPhotoProfile(imageUri: Uri, email: String): Flow<ResponseState<UpdateImageResponse>> = flow {
        emit(ResponseState.Loading)
        try {
            val imagePart = getFilePartFromUri(context, imageUri, "image")
            val emailPart = createPartFromString(email)

            if (imagePart != null) {
                val response = apiService.uploadImage(imagePart, emailPart)
                emit(ResponseState.Success(response))
            } else {
                emit(ResponseState.Error("Failed to create image part"))
            }
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    //CREATE QUIZ FOR TEACHER
    suspend fun createQuizFromTeacher(quiz: CreateQuizRequest): Flow<ResponseState<SubmitQuizResponse>> = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.createQuiz(quiz)
            emit(ResponseState.Success(response))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    //GET CHILD BY PARENT
    suspend fun getChildByParent(): Flow<ResponseState<List<GetStudentResponse>>> = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.getChild()
            emit(ResponseState.Success(response.result))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    // GET QUIZ LIST FOR TEACHER
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

    //DELETE QUIZ FOR TEACHER
    suspend fun deleteQuiz(quizId: String) = flow {
        emit(ResponseState.Loading)
        try{
            val response = apiService.deleteQuiz(quizId)
            if (response.isSuccessful) {
                emit(ResponseState.Success(Unit))
            } else {
                emit(ResponseState.Error("Delete failed with response code ${response.code()}"))
            }
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))

        }
    }
    private fun getFileFromUri(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val file = File(context.cacheDir, context.contentResolver.getFileName(uri))
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun ContentResolver.getFileName(uri: Uri): String {
        var name = ""
        val returnCursor = this.query(uri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }

    //UPLOAD VIDEO
    suspend fun uploadVideoMaterial(context: Context, videoFile: Uri, title: String, desc: String) = flow {
        emit(ResponseState.Loading)
        try {
            val file = getFileFromUri(context, videoFile)
            if (file == null) {
                emit(ResponseState.Error("File not found or could not be created from URI"))
                return@flow
            }
            val requestFile = file.asRequestBody("video/*".toMediaTypeOrNull())
            val videoPart = MultipartBody.Part.createFormData("video", file.name, requestFile)
            val titlePart = title.toRequestBody("text/plain".toMediaTypeOrNull())
            val descPart = desc.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = apiService.uploadVideo(video = videoPart, title = titlePart, desc = descPart)
            emit(ResponseState.Success(response))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    //UPLOAD AUDIO
    suspend fun uploadAudioMaterial(context: Context, audioFile: Uri, title: String) = flow {
        emit(ResponseState.Loading)
        try {
            val file = getFileFromUri(context, audioFile)
            if (file == null) {
                emit(ResponseState.Error("File not found or could not be created from URI"))
                return@flow
            }
            val mimeType = context.contentResolver.getType(audioFile) ?: "audio/*"
            val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
            val audioPart = MultipartBody.Part.createFormData("audio", file.name, requestFile)
            val titlePart = title.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = apiService.uploadAudio(audio = audioPart, title = titlePart)
            emit(ResponseState.Success(response))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    //UPLOAD MATERIAL
    suspend fun uploadMaterial(
        image: MultipartBody.Part,
        videoUrl: RequestBody,
        title: RequestBody,
        description: RequestBody,
        category: RequestBody,
        musicList: List<String>,
        videoList: List<String>
    ) = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.uploadMaterial(
                image = image,
                videoUrl = videoUrl,
                title = title,
                description = description,
                category = category,
                musicList = musicList,
                videoList = videoList
            )
            emit(ResponseState.Success(response))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    //GET AUDIO INFO
    suspend fun getAudioInfo(audioUrl: String) = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.getAudioInfo(audioUrl)
            emit(ResponseState.Success(response))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    //GET VIDEO INFO
    suspend fun getVideoInfo(videoUrl: String) = flow {
        emit(ResponseState.Loading)
        try {
            val response = apiService.getVideoInfo(videoUrl)
            emit(ResponseState.Success(response))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance:UserRepository? = null
        fun getInstance(apiService: ApiService, userPreferences: UserPreferences, context: Context): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreferences, context).also { instance = it }
            }
    }
}