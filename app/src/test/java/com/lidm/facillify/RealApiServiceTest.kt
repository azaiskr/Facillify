package com.lidm.facillify

import androidx.compose.ui.platform.LocalContext
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
    private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Im0ucmFpaGFud2lkYWdkb0BnbWFpbC5jb24iLCJpYXQiOjE3MTY5MTExNzcsImV4cCI6MTcxNjkyMTk3N30.P1Yohcbmsr28nuJbRzjmFAdeMp7hK6UDJhjHcS31M-o" // Ganti dengan token yang valid

    @Before
    fun setUp() {
        apiService = ApiConfig.createApiServiceTest(
            "https://lucky.widzzz.com/",
            token
        )
    }
    @Test
    fun `test getAllThread`() = runBlocking {
        try {
            val response = apiService.getAllThread()
            println(response)
            assertEquals("Success", response.msg)
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
            title = "Thread 2",
            content = "This is a test thread",
            subject = "Subject 2"
        )
        try {
            val response = apiService.createThread(request)
            println(response)
            assertEquals("Success", response.msg)
            assert(response.created_id.isNotEmpty())
        } catch (e: HttpException) {
            println("Error: ${e.response()?.errorBody()?.string()}")
            throw e
        }
    }


}
