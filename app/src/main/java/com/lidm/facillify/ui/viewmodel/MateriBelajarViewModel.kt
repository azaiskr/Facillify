package com.lidm.facillify.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.local.MateriBelajar
import com.lidm.facillify.data.local.VideoItem
import com.lidm.facillify.data.remote.response.AudioInfoResponse
import com.lidm.facillify.data.remote.response.VIdeoInfoResponse
import com.lidm.facillify.data.repository.SiswaRepository
import com.lidm.facillify.data.repository.UserRepository
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MateriBelajarViewModel(
    private val repo: SiswaRepository,
    //private val userRepository: UserRepository
) : ViewModel() {

    //for get material ===============
    private val _audioInfo = MutableStateFlow<ResponseState<AudioInfoResponse>>(ResponseState.Loading)
    val audioInfo: StateFlow<ResponseState<AudioInfoResponse>> get() = _audioInfo

    private val _videoInfo = MutableStateFlow<ResponseState<VIdeoInfoResponse>>(ResponseState.Loading)
    val videoInfo: StateFlow<ResponseState<VIdeoInfoResponse>> get() = _videoInfo
    //for get material ===============

    private val _materialResponse =
        MutableStateFlow<ResponseState<List<MateriBelajar>>>(ResponseState.Loading)
    val materialResponse = _materialResponse.asStateFlow()

    private val _materiDetailResponse =
        MutableStateFlow<ResponseState<MateriBelajar?>>(ResponseState.Loading)
    val materiDetailResponse = _materiDetailResponse.asStateFlow()

    private val _videoContentResponse =
        MutableStateFlow<ResponseState<VideoItem?>>(ResponseState.Loading)
    val videoContentResponse = _videoContentResponse.asStateFlow()

    fun getMaterial() {
        viewModelScope.launch {
            repo.getCombinedMaterialList()
                .collect {
                    _materialResponse.value = it
                }

        }
    }

    fun getMaterialDetail(materiId: String) {
        viewModelScope.launch {
            repo.getMaterialDetail(materiId)
                .collect {
                    _materiDetailResponse.value = it
                }
        }
    }

    fun getVideoContent(materiId: String, videoId: String) {
        viewModelScope.launch {
            repo.getVideoContent(materiId, videoId)
                .collect {
                    _videoContentResponse.value = it
                }
        }
    }


    /*fun getAudioInfo(audioUrl: String) {
        viewModelScope.launch {
            repository.getAudioInfo(audioUrl).collect {
                _audioInfo.value = it
            }
        }
    }

    fun getVideoInfo(videoUrl: String) {
        viewModelScope.launch {
            repository.getVideoInfo(videoUrl).collect {
                _videoInfo.value = it
            }
        }
    }*/
}