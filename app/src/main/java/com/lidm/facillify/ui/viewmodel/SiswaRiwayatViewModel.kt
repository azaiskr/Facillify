package com.lidm.facillify.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.remote.response.DetailAssesment
import com.lidm.facillify.data.remote.response.ProfileResponse
import com.lidm.facillify.data.remote.response.UserProfile
import com.lidm.facillify.data.repository.SiswaRepository
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SiswaRiwayatViewModel(
    private val siswaRepository: SiswaRepository,
): ViewModel() {
    private val _assessment = MutableStateFlow<ResponseState<DetailAssesment>>(ResponseState.Loading)
    val assessment: StateFlow<ResponseState<DetailAssesment>> get() = _assessment

    private val _emailUser = MutableStateFlow<ResponseState<String>>(ResponseState.Loading)
    val emailUser: StateFlow<ResponseState<String>> get() = _emailUser

    private val _profileSiswa = MutableStateFlow<ResponseState<UserProfile>>(ResponseState.Loading)
    val profileSiswa: StateFlow<ResponseState<UserProfile>> get() = _profileSiswa

    init {
        getEmailUser()
    }
    fun getAssessment(email: String) {
        viewModelScope.launch {
            siswaRepository.getAssessment(email).collect { responseState ->
                _assessment.value = responseState
            }
        }
    }

    fun getEmailUser() {
        viewModelScope.launch {
            siswaRepository.getEmailUser().collect { responseState ->
                _emailUser.value = responseState
                Log.d("SiswaRiwayatViewModel", "getEmailUser: ${_emailUser.value}")
            }
        }
    }

    fun getProfileSiswa(email: String) {
        viewModelScope.launch {
            siswaRepository.getProfileData(email).collect {
                _profileSiswa.value = it
            }
        }
    }
}