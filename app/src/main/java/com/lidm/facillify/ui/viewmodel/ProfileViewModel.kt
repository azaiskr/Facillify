package com.lidm.facillify.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.remote.response.ProfileResponse
import com.lidm.facillify.data.remote.response.UpdateImageResponse
import com.lidm.facillify.data.remote.response.UserModelResponse
import com.lidm.facillify.data.repository.UserRepository
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepo: UserRepository
) : ViewModel() {

    private val _profileResponse =
        MutableStateFlow<ResponseState<ProfileResponse>>(ResponseState.Loading)
    val profileResponse = _profileResponse.asStateFlow()

    private val _uploadImageState = MutableStateFlow<ResponseState<UpdateImageResponse>>(ResponseState.Loading)
    val uploadImageState: StateFlow<ResponseState<UpdateImageResponse>> get() = _uploadImageState

    fun logOut() {
        viewModelScope.launch {
            userRepo.clearSession()
        }
    }

    fun getSession(): LiveData<UserModelResponse> {
        return userRepo.getSession().asLiveData()
    }

    fun getUserProfile(email: String) {
        viewModelScope.launch {
            userRepo.getUserProfile(email)
                .collect {
                    when (it) {
                        is ResponseState.Loading -> {
                            _profileResponse.value = ResponseState.Loading
                        }

                        is ResponseState.Success -> {
                            _profileResponse.value = ResponseState.Success(it.data)
                        }

                        is ResponseState.Error -> {
                            _profileResponse.value = ResponseState.Error(it.error)
                        }
                    }
                }
        }
    }

    fun uploadNewProfilePhoto(imageUri: Uri, email: String) {
        viewModelScope.launch {
            userRepo.uploadNewPhotoProfile(imageUri, email).collect {responseState ->
                _uploadImageState.value = responseState
            }
        }
    }
}