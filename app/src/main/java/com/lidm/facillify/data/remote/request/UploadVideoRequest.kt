package com.lidm.facillify.data.remote.request

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

data class UploadVideoRequest(
    @Part val video: MultipartBody.Part,
    @Part("title") val title: RequestBody,
    @Part("desc") val desc: RequestBody,
)