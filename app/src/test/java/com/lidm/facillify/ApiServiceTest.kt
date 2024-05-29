package com.lidm.facillify

import com.lidm.facillify.data.remote.api.ApiService
import com.lidm.facillify.data.remote.request.CreateThreadRequest
import junit.framework.TestCase.assertEquals
import okhttp3.mockwebserver.MockWebServer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class ApiServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test getAllThread`() = runBlocking {
        // Mock response
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                    "msg": "success",
                    "result": [
                        {
                            "_id": "1",
                            "op_email": "user@example.com",
                            "title": "Thread 1",
                            "content": "This is a test thread",
                            "subject": "Subject 1",
                            "time": "2024-05-28T00:00:00Z",
                            "op_name": "User One"
                        }
                    ]
                }
            """.trimIndent())
        mockWebServer.enqueue(mockResponse)

        // Call the API
        val response = apiService.getAllThread()

        // Validate request
        val request = mockWebServer.takeRequest()
        assertEquals("/api/v1/thread", request.path)

        // Validate response
        assertEquals("success", response.msg)
        assertEquals(1, response.result.size)
        val thread = response.result[0]
        assertEquals("1", thread._id)
        assertEquals("user@example.com", thread.op_email)
        assertEquals("Thread 1", thread.title)
        assertEquals("This is a test thread", thread.content)
        assertEquals("Subject 1", thread.subject)
        assertEquals("2024-05-28T00:00:00Z", thread.time)
        assertEquals("User One", thread.op_name)
    }

    @Test
    fun `test createThread`() = runBlocking {
        // Mock response
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                    "msg": "Thread created successfully",
                    "created_id": "1"
                }
            """.trimIndent())
        mockWebServer.enqueue(mockResponse)

        // Prepare request
        val request = CreateThreadRequest(
            op_email = "user@example.com",
            title = "Thread 1",
            content = "This is a test thread",
            subject = "Subject 1"
        )

        // Call the API
        val response = apiService.createThread(request)

        // Validate request
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/api/v1/thread", recordedRequest.path)

        // Validate response
        assertEquals("Thread created successfully", response.msg)
        assertEquals("1", response.created_id)
    }
}