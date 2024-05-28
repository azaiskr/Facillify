package com.lidm.facillify.data.remote.api

import com.lidm.facillify.data.remote.request.CreateCommentThreadRequest
import com.lidm.facillify.data.remote.request.CreateQuizRequest
import com.lidm.facillify.data.remote.request.CreateThreadRequest
import com.lidm.facillify.data.remote.request.LoginRequest
import com.lidm.facillify.data.remote.request.RegisterRequest
import com.lidm.facillify.data.remote.request.SubmitQuizAnswerRequest
import com.lidm.facillify.data.remote.response.AllThreadResponse
import com.lidm.facillify.data.remote.response.CreatedThreadCommentResponse
import com.lidm.facillify.data.remote.response.MessageResponse
import com.lidm.facillify.data.remote.response.QuizDetailResponse
import com.lidm.facillify.data.remote.response.QuizListResponse
import com.lidm.facillify.data.remote.response.SubmitQuizResponse
import com.lidm.facillify.data.remote.response.ThreadCreatedResponse
import com.lidm.facillify.data.remote.response.ThreadDetailResponse
import com.lidm.facillify.data.remote.response.UserModelResponse
import com.lidm.facillify.data.remote.response.VideoListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // AUTH
    @POST("api/v1/register")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequest
    ) : MessageResponse

    @POST("api/v1/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ) : UserModelResponse


    // QUIZ
    @POST("api/v1/quiz")
    suspend fun createQuiz(
        @Body createQuizRequest: CreateQuizRequest
    ) : MessageResponse

    @GET("api/v1/quiz")
    suspend fun getQuizList() : QuizListResponse

    @GET("api/v1/quiz/{quizId}")
    suspend fun getQuiz(
        @Path("quizId") quizId: String,
        @Query("key") key: Boolean = true,
    ) : QuizDetailResponse

    @POST("api/v1/quiz/{quizId}")
    suspend fun submitQuizAnswer(
        @Path("quizId") quizId: String,
        @Body submitQuizAnswerRequest: SubmitQuizAnswerRequest
    ) : SubmitQuizResponse


    // VIDEO
    @GET("api/v1/content")
    suspend fun getVideoList() : VideoListResponse

    @Multipart
    @POST("api/v1/upload")
    suspend fun uploadVideo(
        @Part image: MultipartBody.Part,
        @Part("video_url") videoUrl: RequestBody,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("category") category: RequestBody
    ) : MessageResponse

    //THREAD
    @POST("api/v1/thread")
    suspend fun createThread(
        @Body createThreadRequest: CreateThreadRequest
    ) : ThreadCreatedResponse

    @POST("api/v1/thread/comment")
    suspend fun createThreadComment(
        @Body createCommentThreadRequest: CreateCommentThreadRequest
    ) : CreatedThreadCommentResponse

    @GET("api/v1/thread")
    suspend fun getAllThread() : AllThreadResponse

    @GET("api/v1/thread/{threadId}")
    suspend fun getThreadDetail(
        @Path("threadId") threadId: String
    ) : ThreadDetailResponse
}