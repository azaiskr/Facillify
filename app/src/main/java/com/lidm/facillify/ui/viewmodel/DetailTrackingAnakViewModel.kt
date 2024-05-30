package com.lidm.facillify.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.remote.request.CreateAssessmentForSiswaRequest
import com.lidm.facillify.data.remote.response.CreatedAssessmentForSiswaResponse
import com.lidm.facillify.data.remote.response.DetailAssesment
import com.lidm.facillify.data.repository.SiswaRepository
import com.lidm.facillify.data.repository.UserRepository
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailTrackingAnakViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    private val _assessment = MutableStateFlow<ResponseState<DetailAssesment>>(ResponseState.Loading)
    val assessment: StateFlow<ResponseState<DetailAssesment>> get() = _assessment

    private val _createdAssessment = MutableStateFlow<ResponseState<CreatedAssessmentForSiswaResponse>> (ResponseState.Loading)
    val createdAssessment: StateFlow<ResponseState<CreatedAssessmentForSiswaResponse>> get() = _createdAssessment

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
}