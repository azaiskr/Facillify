package com.lidm.facillify.ui.viewmodel

import androidx.lifecycle.ViewModel
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
import kotlinx.coroutines.launch

class ThreadViewModel(
    private val repository: ThreadRepository
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

    fun getEmailUser() {
        viewModelScope.launch {
            repository.getEmailUser().collect { responseState ->
                _emailUser.value = responseState
            }
        }
    }
}
