package com.lidm.facillify.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.remote.request.MaterialRequest
import com.lidm.facillify.data.remote.response.MaterialResponse
import com.lidm.facillify.data.remote.response.UploadVideoAudioResponse
import com.lidm.facillify.data.repository.UserRepository
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class TambahMateriBelajarViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val _createdMaterial = MutableStateFlow<ResponseState<MaterialResponse>>(ResponseState.Loading)
    val createdMaterial: StateFlow<ResponseState<MaterialResponse>> get() = _createdMaterial

    private val _uploadedVideoUrls = MutableStateFlow<ResponseState<List<UploadVideoAudioResponse>>>(ResponseState.Loading)
    val uploadedVideoUrls: StateFlow<ResponseState<List<UploadVideoAudioResponse>>> get() = _uploadedVideoUrls

    private val _uploadedAudioUrls = MutableStateFlow<ResponseState<List<UploadVideoAudioResponse>>>(ResponseState.Loading)
    val uploadedAudioUrls: StateFlow<ResponseState<List<UploadVideoAudioResponse>>> get() = _uploadedAudioUrls

    fun uploadVideo(context: Context, videoUri: Uri, title: String, description: String) {
        viewModelScope.launch {
            repository.uploadVideoMaterial(context, videoUri, title, description).collect { response ->
                when (response) {
                    is ResponseState.Loading -> _uploadedVideoUrls.value = ResponseState.Loading
                    is ResponseState.Success -> {
                        val currentList = (_uploadedVideoUrls.value as? ResponseState.Success)?.data ?: emptyList()
                        _uploadedVideoUrls.value = ResponseState.Success(currentList + response.data)
                    }
                    is ResponseState.Error -> _uploadedVideoUrls.value = ResponseState.Error(response.error)
                }
            }
        }
    }

    fun uploadAudio(context: Context, audioUri: Uri, title: String) {
        viewModelScope.launch {
            repository.uploadAudioMaterial(context, audioUri, title).collect { response ->
                when (response) {
                    is ResponseState.Loading -> _uploadedAudioUrls.value = ResponseState.Loading
                    is ResponseState.Success -> {
                        val currentList = (_uploadedAudioUrls.value as? ResponseState.Success)?.data ?: emptyList()
                        _uploadedAudioUrls.value = ResponseState.Success(currentList + response.data)
                    }
                    is ResponseState.Error -> _uploadedAudioUrls.value = ResponseState.Error(response.error)
                }
            }
        }
    }

    fun clearUploadedVideoUrls() {
        _uploadedVideoUrls.value = ResponseState.Success(emptyList())
    }

    fun clearUploadedAudioUrls() {
        _uploadedAudioUrls.value = ResponseState.Success(emptyList())
    }

    fun createMaterial(
        image: Uri,
        videoUrls: List<String>,
        audioUrls: List<String>,
        title: RequestBody,
        description: RequestBody,
        category: RequestBody
    ) {
        viewModelScope.launch {
            val imageNull: MultipartBody.Part? = null
            val materialRequest = MaterialRequest(
                image = imageNull!!,
                videoUrl = "https://www.youtube.com/watch?v=ibLZM7DZNiU".toRequestBody("text/plain".toMediaTypeOrNull()).toString(),
                title = title,
                description = description,
                category = category,
                musicList = audioUrls,
                videoList = videoUrls
            )

            repository.uploadMaterial(
                imageUri = image,
                videoUrl = materialRequest.videoUrl.toRequestBody(),
                title = materialRequest.title,
                description = materialRequest.description,
                category = materialRequest.category,
                musicList = materialRequest.musicList,
                videoList = materialRequest.videoList
            ).collect {
                _createdMaterial.value = it
            }
        }
    }
}
