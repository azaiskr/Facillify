package com.lidm.facillify.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.remote.response.UpdateEmailParentResponse
import com.lidm.facillify.data.remote.response.UserModelResponse
import com.lidm.facillify.data.repository.SiswaRepository
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UpdateParentEmailViewModel (
    private val siswaRepo: SiswaRepository
) : ViewModel() {

    private val _updateEmailResponse = MutableStateFlow<ResponseState<UpdateEmailParentResponse>>(ResponseState.Loading)
    val updateEmailResponse = _updateEmailResponse


    fun updateParentEmail(studentEmail: String, parentEmail: String) {
        viewModelScope.launch {
            siswaRepo.updateParentEmail(
                studentEmail,parentEmail
            ).collectLatest { response ->
                when(response){
                    is ResponseState.Loading -> {
                        _updateEmailResponse.value = ResponseState.Loading
                    }
                    is ResponseState.Success -> {
                        _updateEmailResponse.value = ResponseState.Success(response.data)
                    }
                    is ResponseState.Error -> {
                        _updateEmailResponse.value = ResponseState.Error(response.error)
                    }
                }
            }
        }
    }

    fun getSession(): LiveData<UserModelResponse> {
        return siswaRepo.getSession().asLiveData()
    }


}