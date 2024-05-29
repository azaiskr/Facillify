package com.lidm.facillify.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.remote.response.UserModelResponse
import com.lidm.facillify.data.repository.UserRepository
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _loginState =
        MutableStateFlow<ResponseState<UserModelResponse>>(ResponseState.Loading)
    val loginState: StateFlow<ResponseState<UserModelResponse>> = _loginState

    fun login(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            userRepository.loginUser(email, password)
                .collect { responseState ->
                    when (responseState) {
                        is ResponseState.Loading -> {
                            _loginState.value = ResponseState.Loading
                        }

                        is ResponseState.Success -> {
                            _loginState.value = ResponseState.Success(responseState.data)
                            saveSession(responseState.data)
                        }

                        is ResponseState.Error -> {
                            _loginState.value = ResponseState.Error(responseState.error)
                        }
                    }
                }
        }
    }

    private fun saveSession(user: UserModelResponse){
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }


}