package com.lidm.facillify.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.remote.response.QuizListResponse
import com.lidm.facillify.data.repository.SiswaRepository
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeSiswaViewModel (
    private val repo: SiswaRepository
) : ViewModel() {

    private val _quizList = MutableStateFlow<ResponseState<QuizListResponse>>(ResponseState.Loading)
    val quizList = _quizList.asStateFlow()

    fun getQuizList() {
        viewModelScope.launch {
            repo.getQuizList()
                .collect {response ->
                    _quizList.value = response
                }
        }
    }

}