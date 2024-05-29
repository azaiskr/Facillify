package com.lidm.facillify.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lidm.facillify.data.remote.response.UserModelResponse
import com.lidm.facillify.data.repository.UserRepository

class MainViewModel (
    private val userRepository: UserRepository,
):ViewModel() {
    fun getSession(): LiveData<UserModelResponse> {
        return userRepository.getSession().asLiveData()
    }
}