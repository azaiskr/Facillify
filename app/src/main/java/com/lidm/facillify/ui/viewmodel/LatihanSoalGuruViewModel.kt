package com.lidm.facillify.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.remote.response.QuizListResponse
import com.lidm.facillify.data.repository.UserRepository
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LatihanSoalGuruViewModel (
    private val repo:UserRepository
) : ViewModel() {
    private val _quizList = MutableStateFlow<ResponseState<QuizListResponse>>(ResponseState.Loading)
    val quizList = _quizList.asStateFlow()

    private val _deleteResponse = MutableStateFlow<ResponseState<Unit>>(ResponseState.Loading)
    val deleteResponse = _deleteResponse.asStateFlow()

    fun getQuizList() {
        viewModelScope.launch {
            repo.getQuizList()
                .collect { response ->
                    _quizList.value =response
                }
        }
    }

    fun deleteQuiz(id: String) {
        viewModelScope.launch {
            repo.deleteQuiz(id)
                .collect { response ->
                    _deleteResponse.value = response
                    Log.d("LatihanGuruViewModel", "deleteQuiz: $response")
                    if (response is ResponseState.Success) {
                        getQuizList()
                    }
                }
        }
    }

    fun resetDeleteResponse() {
        _deleteResponse.value = ResponseState.Loading
    }
}