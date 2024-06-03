package com.lidm.facillify.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.remote.request.CreateAssessmentForSiswaRequest
import com.lidm.facillify.data.remote.response.CreatedAssessmentForSiswaResponse
import com.lidm.facillify.data.remote.response.DetailAssesment
import com.lidm.facillify.data.remote.response.GradeHistory
import com.lidm.facillify.data.remote.response.UserModelResponse
import com.lidm.facillify.data.repository.SiswaRepository
import com.lidm.facillify.data.repository.UserRepository
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailTrackingAnakViewModel(
    private val userRepository: UserRepository,
    private val siswaRepository: SiswaRepository
): ViewModel() {

    private val _assessment = MutableStateFlow<ResponseState<DetailAssesment>>(ResponseState.Loading)
    val assessment: StateFlow<ResponseState<DetailAssesment>> get() = _assessment

    private val _createdAssessment = MutableStateFlow<ResponseState<CreatedAssessmentForSiswaResponse>> (ResponseState.Loading)
    val createdAssessment: StateFlow<ResponseState<CreatedAssessmentForSiswaResponse>> get() = _createdAssessment

    val _gradeSiswa = MutableStateFlow<ResponseState<List<GradeHistory>>>(ResponseState.Loading)
    val gradeSiswa: StateFlow<ResponseState<List<GradeHistory>>> get() = _gradeSiswa

    fun createAssessment(assesment: CreateAssessmentForSiswaRequest) {
        viewModelScope.launch {
            userRepository.createAssessment(assesment).collect {responsState ->
                _createdAssessment.value = responsState
            }
        }
    }

    fun resetCreateAssessment() {
        _createdAssessment.value = ResponseState.Loading
    }

    fun getGradeHistoryStudent(email: String){
        viewModelScope.launch {
            siswaRepository.getHistoryStudent(email).collect { responseState ->
                _gradeSiswa.value = responseState
            }
        }
    }

    fun getSession(): LiveData<UserModelResponse> {
        return userRepository.getSession().asLiveData()
    }
}