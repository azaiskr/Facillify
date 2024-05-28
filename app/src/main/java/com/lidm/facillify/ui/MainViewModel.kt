package com.lidm.facillify.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lidm.facillify.data.remote.response.UserModelResponse
import com.lidm.facillify.data.repository.AuthRepository

class MainViewModel (
    private val authRepository: AuthRepository,
):ViewModel() {
    fun getSession(): LiveData<UserModelResponse> {
        return authRepository.getSession().asLiveData()
    }
}