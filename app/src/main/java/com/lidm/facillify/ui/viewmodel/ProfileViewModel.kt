package com.lidm.facillify.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.remote.response.ProfileResponse
import com.lidm.facillify.data.remote.response.UserModelResponse
import com.lidm.facillify.data.repository.UserRepository
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepo: UserRepository
) : ViewModel() {

    private val _profileResponse =
        MutableStateFlow<ResponseState<ProfileResponse>>(ResponseState.Loading)
    val profileResponse = _profileResponse.asStateFlow()

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
            try {
                userRepo.getUserProfile(email)
                    .catch {
                        _profileResponse.value = ResponseState.Error(it.message)
                        Log.d("error", it.message.toString())
                    }
                    .collect{
                        _profileResponse.value = ResponseState.Success(it)
                        Log.d("success", it.toString())
                    }
            } catch (e:Exception) {
                _profileResponse.value = ResponseState.Error(e.message)
                Log.d("error", e.message.toString())
            }

        }
    }
}