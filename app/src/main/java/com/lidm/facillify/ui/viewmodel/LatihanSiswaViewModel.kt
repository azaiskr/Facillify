package com.lidm.facillify.ui.viewmodel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.local.HasilLatihan
import com.lidm.facillify.data.remote.request.SubmitQuizAnswerRequest
import com.lidm.facillify.data.remote.response.QuizDetailResponse
import com.lidm.facillify.data.remote.response.QuizListResponse
import com.lidm.facillify.data.remote.response.SubmitQuizResponse
import com.lidm.facillify.data.repository.SiswaRepository
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LatihanSiswaViewModel (private val repo: SiswaRepository) : ViewModel() {

    private val _quizList = MutableStateFlow<ResponseState<QuizListResponse>>(ResponseState.Loading)
    val quizList = _quizList.asStateFlow()

    private val _quiz = MutableStateFlow<ResponseState<QuizDetailResponse>>(ResponseState.Loading)
    val quiz = _quiz.asStateFlow()

    private val _quizResult = MutableStateFlow<ResponseState<SubmitQuizResponse>>(ResponseState.Loading)
    val quizResult: StateFlow<ResponseState<SubmitQuizResponse>> = _quizResult

    private val _emailUser = MutableStateFlow<ResponseState<String>>(ResponseState.Loading)
    val emailUser = _emailUser.asStateFlow()

    fun getEmailuser() {
        viewModelScope.launch {
            repo.getEmailUser()
                .collect {
                    _emailUser.value = it
                }
        }
    }

    fun getQuizList() {
        viewModelScope.launch {
            repo.getQuizList()
                .collect {response ->
                    _quizList.value = response
                }
        }
    }

    fun getQuiz(quizId : String) {
        viewModelScope.launch {
            repo.getQuizDetail(quizId)
                .collect {
                    _quiz.value = it
                }
        }
    }

    fun submitQuiz(quizId: String, submitRequest: SubmitQuizAnswerRequest) {
        viewModelScope.launch {
            repo.submitQuiz(quizId, submitRequest)
                .collect {
                    Log.d("LatihanSiswaViewModel", "submitQuiz: $it")
                    _quizResult.value = it
                }
        }
    }

}