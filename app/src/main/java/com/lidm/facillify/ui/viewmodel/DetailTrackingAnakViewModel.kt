package com.lidm.facillify.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.lidm.facillify.data.remote.response.DetailAssesment
import com.lidm.facillify.data.repository.SiswaRepository
import com.lidm.facillify.data.repository.UserRepository
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailTrackingAnakViewModel(
    private val userRepository: UserRepository,
    private val siswaRepository: SiswaRepository
): ViewModel() {

    private val _assessment = MutableStateFlow<ResponseState<DetailAssesment>>(ResponseState.Loading)
    val assessment: StateFlow<ResponseState<DetailAssesment>> get() = _assessment

}