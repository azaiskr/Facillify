package com.lidm.facillify

import com.lidm.facillify.data.remote.api.ApiConfig
import com.lidm.facillify.data.remote.api.ApiService
import com.lidm.facillify.data.remote.request.CreateThreadRequest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class RealApiServiceTest {

    private lateinit var apiService: ApiService
    private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Im0ucmFpaGFud2lkYWdkb0BnbWFpbC5jb24iLCJpYXQiOjE3MTY4ODY3OTYsImV4cCI6MTcxNjg5NzU5Nn0.q3vp9syB9mCzXQRCVdV2divzG8DcDfaszwVENAc5k8w" // Ganti dengan token yang valid

    @Before
    fun setUp() {
        apiService = ApiConfig.TestCreateApiService("https://lucky.widzzz.com/", token)
    }
    @Test
    fun `test getAllThread`() = runBlocking {
        try {
            val response = apiService.getAllThread()
            println(response)
            assertEquals("success", response.msg)
            assert(response.result.isNotEmpty())
        } catch (e: HttpException) {
            println("Error: ${e.response()?.errorBody()?.string()}")
            throw e
        }
    }

    @Test
    fun `test createThread`() = runBlocking {
        val request = CreateThreadRequest(
            op_email = "user@example.com",
            title = "Thread 1",
            content = "This is a test thread",
            subject = "Subject 1"
        )
        try {
            val response = apiService.createThread(request)
            println(response)
            assertEquals("Thread created successfully", response.msg)
            assert(response.created_id.isNotEmpty())
        } catch (e: HttpException) {
            println("Error: ${e.response()?.errorBody()?.string()}")
            throw e
        }
    }


}
