package com.lidm.facillify.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.remote.response.GetStudentResponse
import com.lidm.facillify.data.remote.response.ProfileResponse
import com.lidm.facillify.data.remote.response.UserModelResponse
import com.lidm.facillify.data.repository.UserRepository
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TrackingAnakViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    private val _listStudent = MutableStateFlow<ResponseState<List<GetStudentResponse>>>(ResponseState.Loading)
    val listStudent: StateFlow<ResponseState<List<GetStudentResponse>>> get() = _listStudent

    private val _profileUrlMap = mutableMapOf<String, MutableStateFlow<ResponseState<ProfileResponse>>>()

    fun getProfileUrlFlow(email: String): StateFlow<ResponseState<ProfileResponse>> {
        return _profileUrlMap.getOrPut(email) {
            MutableStateFlow<ResponseState<ProfileResponse>>(ResponseState.Loading)
        }
    }

    fun getProfileUrl(email: String) {
        viewModelScope.launch {
            val profileStateFlow = _profileUrlMap.getOrPut(email) {
                MutableStateFlow<ResponseState<ProfileResponse>>(ResponseState.Loading)
            }
            userRepository.getUserProfile(email).collect { responseState ->
                profileStateFlow.value = responseState
            }
        }
    }

    fun getAllStudent() {
        viewModelScope.launch {
            userRepository.getAllStudent().collect { responseState ->
                _listStudent.value = responseState
            }
        }
    }

    fun getSession(): LiveData<UserModelResponse> {
        return userRepository.getSession().asLiveData()
    }

}