package com.lidm.facillify.data.remote.request

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

data class MaterialRequest(
    @Part val image: MultipartBody.Part,
    @Part("video_url") val videoUrl: String = "",
    @Part("title") val title: RequestBody,
    @Part("description") val description: RequestBody,
    @Part("category") val category: RequestBody,
    @Part("music_list") val musicList: List<String>,
    @Part("video_list") val videoList: List<String>
)