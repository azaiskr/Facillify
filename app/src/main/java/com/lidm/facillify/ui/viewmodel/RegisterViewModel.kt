package com.lidm.facillify.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.remote.response.MessageResponse
import com.lidm.facillify.data.repository.UserRepository
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel (
    private val userRepository: UserRepository
) : ViewModel() {

    private val _registerResponse = MutableStateFlow<ResponseState<MessageResponse>>(ResponseState.Loading)
    val registerResponse: StateFlow<ResponseState<MessageResponse>> = _registerResponse

    fun registerMurid(type: String, email: String, name: String, password: String, pod: String, dob: String, gender: String, address: String, phone_number: String, religion: String, nisn: String) {
        viewModelScope.launch {
            userRepository.registerMurid(
                type = type,
                email = email,
                name = name,
                password = password,
                pod = pod,
                dob = dob,
                gender = gender,
                address = address,
                phone_number = phone_number,
                religion = religion,
                nisn = nisn
            ).collect { responseState ->
                when (responseState) {
                    is ResponseState.Loading -> {
                        _registerResponse.value = ResponseState.Loading
                    }
                    is ResponseState.Success -> {
                        _registerResponse.value = ResponseState.Success(responseState.data)
                    }
                    is ResponseState.Error -> {
                        _registerResponse.value = ResponseState.Error(responseState.error)
                    }
                }
            }
        }
    }

    fun registerGuru(type: String, email: String, name: String, password: String, pod: String, dob: String, gender: String, address: String, phone_number: String, religion: String, nip: String) {
        viewModelScope.launch {
            userRepository.registerGuru(type = type, email = email, password = name, name = password, pod = pod, dob = dob, gender = gender, address = address, phone_number = phone_number, religion = religion, nip = nip).collect { responseState ->
                when (responseState) {
                    is ResponseState.Loading -> {
                        _registerResponse.value = ResponseState.Loading
                    }
                    is ResponseState.Success -> {
                        _registerResponse.value = ResponseState.Success(responseState.data)
                        Log.d("RegisterViewModel", "registerGuru: ${responseState.data}")
                    }
                    is ResponseState.Error -> {
                        _registerResponse.value = ResponseState.Error(responseState.error)
                    }
                }
            }
        }
    }

    fun registerOrtu(
        type: String,
        email: String,
        name: String,
        password: String,
        pod: String,
        dob: String,
        gender: String,
        address: String,
        phone_number: String,
        religion: String,
        job: String
    ) {
        viewModelScope.launch {
            userRepository.registerOrtu(type = type, email = email, password = password, name = name, pod = pod, dob = dob, gender = gender, address = address, phone_number = phone_number, religion = religion, job = job).collect { responseState ->
                when (responseState) {
                    is ResponseState.Loading -> {
                        _registerResponse.value = ResponseState.Loading
                    }
                    is ResponseState.Success -> {
                        _registerResponse.value = ResponseState.Success(responseState.data)
                        Log.d("RegisterViewModel", "registerOrtu: ${responseState.data}")
                    }
                    is ResponseState.Error -> {
                        _registerResponse.value = ResponseState.Error(responseState.error)
                    }
                }
            }
        }
    }
}