package com.lidm.facillify.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.remote.request.CreateCommentThreadRequest
import com.lidm.facillify.data.remote.request.CreateThreadRequest
import com.lidm.facillify.data.remote.response.CreatedThreadCommentResponse
import com.lidm.facillify.data.remote.response.ThreadCreatedResponse
import com.lidm.facillify.data.remote.response.ThreadDetailResponse
import com.lidm.facillify.data.remote.response.ThreadResponse
import com.lidm.facillify.data.remote.response.UserModelResponse
import com.lidm.facillify.data.repository.ThreadRepository
import com.lidm.facillify.data.repository.UserRepository
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ThreadViewModel(
    private val repository: ThreadRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _threads = MutableStateFlow<ResponseState<List<ThreadResponse>>>(ResponseState.Loading)
    val threads: StateFlow<ResponseState<List<ThreadResponse>>> get() = _threads

    private val _threadDetail = MutableStateFlow<ResponseState<ThreadDetailResponse>>(ResponseState.Loading)
    val threadDetail: StateFlow<ResponseState<ThreadDetailResponse>> get() = _threadDetail

    private val _createThreadResult = MutableStateFlow<ResponseState<ThreadCreatedResponse>>(ResponseState.Loading)
    val createThreadResult: StateFlow<ResponseState<ThreadCreatedResponse>> get() = _createThreadResult

    private val _createCommentResult = MutableStateFlow<ResponseState<CreatedThreadCommentResponse>>(ResponseState.Loading)
    val createCommentResult: StateFlow<ResponseState<CreatedThreadCommentResponse>> get() = _createCommentResult

    private val _imageProfileUser = MutableStateFlow<ResponseState<UserModelResponse>>(ResponseState.Loading)
    val getImageProfile: StateFlow<ResponseState<UserModelResponse>> get() = _imageProfileUser

    private val _emailUser = MutableStateFlow<ResponseState<String>>(ResponseState.Loading)
    val emailUser: StateFlow<ResponseState<String>> get() = _emailUser

    private val _dateThread = MutableStateFlow<ResponseState<String>>(ResponseState.Loading)
    val dateThread: StateFlow<ResponseState<String>> get() = _dateThread

    private val _subjectThread = MutableStateFlow<ResponseState<String>>(ResponseState.Loading)
    val subjectThread: StateFlow<ResponseState<String>> get() = _subjectThread

    private val _totalComments = MutableStateFlow<Map<String, Int>>(emptyMap())
    val totalComments: StateFlow<Map<String, Int>> get() = _totalComments

    private val _profileImageUrlMap = MutableStateFlow<Map<String, String>>(emptyMap())
    val profileImageUrlMap: StateFlow<Map<String, String>> get() = _profileImageUrlMap

    init {
        getAllThreads()
    }

    fun createThread(request: CreateThreadRequest) {
        viewModelScope.launch {
            repository.createThread(request).collect { responseState ->
                _createThreadResult.value = responseState
            }
        }
    }

    fun createThreadComment(request: CreateCommentThreadRequest) {
        viewModelScope.launch {
            repository.createThreadComment(request).collect { responseState ->
                _createCommentResult.value = responseState
            }
        }
    }

    fun getAllThreads() {
        viewModelScope.launch {
            repository.getAllThreads().collect { responseState ->
                _threads.value = responseState
            }
        }
    }

    fun getThreadDetail(threadId: String) {
        viewModelScope.launch {
            repository.getThreadDetail(threadId).collect { responseState ->
                _threadDetail.value = responseState
            }
        }
    }

    fun resetCreateThreadResult() {
        _createThreadResult.value = ResponseState.Loading
    }

    fun resetCreateCommentResult() {
        _createCommentResult.value = ResponseState.Loading
    }

    fun getEmailUser() {
        viewModelScope.launch {
            repository.getEmailUser().collect { responseState ->
                _emailUser.value = responseState
            }
        }
    }

    fun getDateThread(id: String) {
        viewModelScope.launch {
            _threads.collectLatest { data ->
                if (data is ResponseState.Success) {
                    val thread = data.data.find { it._id == id }
                    _dateThread.value = ResponseState.Success(thread?.time ?: "")
                    Log.d("ThreadViewModel", "getDateThread: ${_dateThread.value}")
                }
            }
        }
    }

    fun getSubjectThread(id: String) {
        viewModelScope.launch {
            _threads.collectLatest { data ->
                if (data is ResponseState.Success) {
                    val thread = data.data.find { it._id == id }
                    _subjectThread.value = ResponseState.Success(thread?.subject ?: "")
                    Log.d("ThreadViewModel", "getSubjectThread: ${_subjectThread.value}")
                }
            }
        }
    }

    fun getTotalComment(id: String) {
        viewModelScope.launch {
            val totalCommentsMap = _totalComments.value.toMutableMap()
            _threadDetail.collectLatest { data ->
                if (data is ResponseState.Success) {
                    val thread = data.data
                    totalCommentsMap[id] = thread.comments.size
                    _totalComments.value = totalCommentsMap
                    Log.d("ThreadViewModel", "getTotalComment: ${_totalComments.value}")
                }
            }
        }
    }

    fun getSession(): LiveData<UserModelResponse> {
        return userRepository.getSession().asLiveData()
    }

    fun getUserProfile(email: String) {
        viewModelScope.launch {
            userRepository.getUserProfile(email).collect { responseState ->
                if (responseState is ResponseState.Success) {
                    val updatedMap = _profileImageUrlMap.value.toMutableMap()
                    updatedMap[email] = responseState.data.result.profile_image_url.toString()
                    _profileImageUrlMap.value = updatedMap
                }
            }
        }
    }
}
