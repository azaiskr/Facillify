package com.lidm.facillify.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.remote.response.GetStudentResponse
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

    fun getAllStudent() {
        viewModelScope.launch {
            userRepository.getAllStudent().collect { responseState ->
                _listStudent.value = responseState
            }
        }
    }
}