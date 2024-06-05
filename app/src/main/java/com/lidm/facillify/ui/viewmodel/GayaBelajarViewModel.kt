package com.lidm.facillify.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.remote.request.LearningStyleRequest
import com.lidm.facillify.data.remote.response.LearningStyleRespons
import com.lidm.facillify.data.repository.SiswaRepository
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GayaBelajarViewModel(
    private val siswaRepository: SiswaRepository
): ViewModel() {

    private val _learningStyle = MutableStateFlow<ResponseState<LearningStyleRespons>>(ResponseState.Loading)
    val learningStyle: StateFlow<ResponseState<LearningStyleRespons>> = _learningStyle

    fun putLearningStye(request: LearningStyleRequest){
        viewModelScope.launch {
            siswaRepository.postLearningStyle(request).collect { responseState ->
                when(responseState) {
                    is ResponseState.Loading -> {
                        _learningStyle.value = ResponseState.Loading
                    }
                    is ResponseState.Success -> {
                        _learningStyle.value = ResponseState.Success(responseState.data)
                    }
                    is ResponseState.Error -> {
                        _learningStyle.value = ResponseState.Error(responseState.error)
                    }
                }
            }
        }
    }
}