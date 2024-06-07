package com.lidm.facillify.data.remote.request

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

data class UploadAudioRequest(
    @Part val audio: MultipartBody.Part,
    @Part("title") val title: RequestBody,
)
