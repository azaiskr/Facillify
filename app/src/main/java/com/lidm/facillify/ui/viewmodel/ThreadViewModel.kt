package com.lidm.facillify.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.remote.request.CreateCommentThreadRequest
import com.lidm.facillify.data.remote.request.CreateThreadRequest
import com.lidm.facillify.data.remote.response.CreatedThreadCommentResponse
import com.lidm.facillify.data.remote.response.ThreadCreatedResponse
import com.lidm.facillify.data.remote.response.ThreadDetailResponse
import com.lidm.facillify.data.remote.response.ThreadResponse
import com.lidm.facillify.data.repository.ThreadRepository
import kotlinx.coroutines.launch

class ThreadViewModel(private val repository: ThreadRepository) : ViewModel() {

    private val _threads = MutableLiveData<List<ThreadResponse>>()
    val threads: LiveData<List<ThreadResponse>> get() = _threads

    private val _threadDetail = MutableLiveData<ThreadDetailResponse?>()
    val threadDetail: LiveData<ThreadDetailResponse?> get() = _threadDetail

    private val _createThreadResult = MutableLiveData<ThreadCreatedResponse?>()
    val createThreadResult: LiveData<ThreadCreatedResponse?> get() = _createThreadResult

    private val _createCommentResult = MutableLiveData<CreatedThreadCommentResponse?>()
    val createCommentResult: LiveData<CreatedThreadCommentResponse?> get() = _createCommentResult

    fun createThread(request: CreateThreadRequest) {
        viewModelScope.launch {
            val result = repository.createThread(request)
            result.onSuccess {
                _createThreadResult.postValue(it)
            }.onFailure {
                _createThreadResult.postValue(null)
            }
        }
    }

    fun createThreadComment(request: CreateCommentThreadRequest) {
        viewModelScope.launch {
            val result = repository.createThreadComment(request)
            result.onSuccess {
                _createCommentResult.postValue(it)
            }.onFailure {
                _createCommentResult.postValue(null)
            }
        }
    }

    fun getAllThreads() {
        viewModelScope.launch {
            val result = repository.getAllThreads()
            result.onSuccess {
                _threads.postValue(it.result)
            }.onFailure {
                _threads.postValue(emptyList())
            }
        }
    }

    fun getThreadDetail(threadId: String) {
        viewModelScope.launch {
            val result = repository.getThreadDetail(threadId)
            result.onSuccess {
                _threadDetail.postValue(it)
            }.onFailure {
                _threadDetail.postValue(null)
            }
        }
    }
}