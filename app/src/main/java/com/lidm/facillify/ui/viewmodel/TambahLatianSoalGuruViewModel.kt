package com.lidm.facillify.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.remote.request.CreateQuizRequest
import com.lidm.facillify.data.remote.request.Question
import com.lidm.facillify.data.remote.response.SubmitQuizResponse
import com.lidm.facillify.data.repository.UserRepository
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TambahLatianSoalGuruViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    private val _createdQuiz = MutableStateFlow<ResponseState<SubmitQuizResponse>>(ResponseState.Loading)
    val createdQuiz: StateFlow<ResponseState<SubmitQuizResponse>> get() = _createdQuiz

    private val _latihanSoal = MutableStateFlow<List<Question>>(emptyList())
    val latihanSoal: StateFlow<List<Question>> = _latihanSoal

    private val _judulSoal = MutableStateFlow("")
    val judulSoal: StateFlow<String> = _judulSoal

    private val _deskripsiSoal = MutableStateFlow("")
    val deskripsiSoal: StateFlow<String> = _deskripsiSoal

    private val _durasi = MutableStateFlow(0)
    val durasi: StateFlow<Int> = _durasi

    fun setJudulSoal(judul: String) {
        _judulSoal.value = judul
    }

    fun setDeskripsiSoal(deskripsi: String) {
        _deskripsiSoal.value = deskripsi
    }

    fun setDurasi(durasi: Int) {
        _durasi.value = durasi
    }

    fun addQuestion(question: Question) {
        _latihanSoal.value = _latihanSoal.value + question
    }

    fun removeQuestion(index: Int) {
        _latihanSoal.value = _latihanSoal.value.toMutableList().apply { removeAt(index) }
    }

    fun createQuiz(quiz: CreateQuizRequest) {
        viewModelScope.launch {
            userRepository.createQuizFromTeacher(quiz).collect {
                _createdQuiz.value = it
            }
        }
    }
}