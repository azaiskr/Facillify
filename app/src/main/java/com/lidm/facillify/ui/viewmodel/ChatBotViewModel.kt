package com.lidm.facillify.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.ChatMessage
import com.lidm.facillify.data.local.dao.AppDatabase
import com.lidm.facillify.data.local.dao.ChatMessageEntity
import com.lidm.facillify.data.remote.api.ChatbotApiService
import com.lidm.facillify.data.remote.request.ChatbotRequest
import com.lidm.facillify.data.remote.response.DetailAssesment
import com.lidm.facillify.data.remote.response.GradeHistory
import com.lidm.facillify.data.remote.response.UserProfile
import com.lidm.facillify.data.repository.SiswaRepository
import com.lidm.facillify.util.ResponseState
import com.lidm.facillify.util.getCurrentDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class ChatViewModel(
    private val chatbotApiService: ChatbotApiService,
    private val siswaRepository: SiswaRepository,
    private val database: AppDatabase
) : ViewModel() {

    private val chatMessageDao = database.chatMessageDao()

    private val _messages = MutableStateFlow<List<ChatMessage>>(listOf(
        ChatMessage(
            text = "Halo, saya FACILLIFY AI. Bagaimana saya bisa membantu Anda hari ini?",
            isUser = false,
            timestamp = getCurrentDateTime()
        )
    ))
    val messages: StateFlow<List<ChatMessage>>
        get() = _messages

    private val _profileSiswa = MutableStateFlow<ResponseState<UserProfile>>(ResponseState.Loading)
    val profileSiswa: StateFlow<ResponseState<UserProfile>>
        get() = _profileSiswa

    private val _assessmentSiswa = MutableStateFlow<ResponseState<DetailAssesment>>(ResponseState.Loading)
    val assessmentSiswa: StateFlow<ResponseState<DetailAssesment>>
        get() = _assessmentSiswa

    private val _historySiswa = MutableStateFlow<ResponseState<List<GradeHistory>>>(ResponseState.Loading)
    val historySiswa: StateFlow<ResponseState<List<GradeHistory>>>
        get() = _historySiswa

    private val _email = MutableStateFlow<ResponseState<String>>(ResponseState.Loading)
    val email: StateFlow<ResponseState<String>>
        get() = _email

    init {
        loadEmailAndData()
    }

    private fun loadEmailAndData() {
        viewModelScope.launch {
            try {
                siswaRepository.getEmailUser().collect { response ->
                    _email.value = response
                    if (response is ResponseState.Success) {
                        val email = response.data
                        loadData(email)
                    } else {
                        _messages.value += ChatMessage(
                            text = "Error: ${response}",
                            isUser = false,
                            timestamp = getCurrentDateTime()
                        )
                    }
                }
            } catch (e: Exception) {
                _messages.value += ChatMessage(
                    text = "Error: ${e.message}",
                    isUser = false,
                    timestamp = getCurrentDateTime()
                )
            }
        }
    }

    private fun loadData(email: String) {
        viewModelScope.launch {
            siswaRepository.getProfileData(email).collect {
                _profileSiswa.value = it
            }
            siswaRepository.getAssessment(email).collect {
                _assessmentSiswa.value = it
            }
            siswaRepository.getHistoryStudent(email).collect {
                _historySiswa.value = it
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(content: String) {
        val currentTime = getCurrentDateTime()
        val userMessage = ChatMessage(
            text = content,
            isUser = true,
            timestamp = currentTime
        )
        _messages.value += userMessage

        viewModelScope.launch {
            try {
                saveMessage(userMessage)
                if (_email.value is ResponseState.Success &&
                    _profileSiswa.value is ResponseState.Success &&
                    _assessmentSiswa.value is ResponseState.Success &&
                    _historySiswa.value is ResponseState.Success) {

                    val email = (_email.value as ResponseState.Success).data
                    val profile = (_profileSiswa.value as ResponseState.Success).data
                    val assessment = (_assessmentSiswa.value as ResponseState.Success).data
                    val history = (_historySiswa.value as ResponseState.Success).data

                    val userInfoMessageContent = buildUserInfoMessage(profile, assessment, history)

                    val systemMessage = com.lidm.facillify.data.remote.response.Message(
                        role = "system",
                        content = "Anda adalah chatbot bernama FACILLIFY AI dari aplikasi Learning Management System (LMS) Faciliffy. Jawab semua pertanyaan dalam bahasa Indonesia. Berikut adalah identitas dari pengguna atau user yang sedang berinteraksi dengan Anda: $userInfoMessageContent"
                    )

                    val userMessageApi = com.lidm.facillify.data.remote.response.Message(
                        role = "user",
                        content = content
                    )
                    val request = ChatbotRequest(
                        model = "gpt-4o",
                        messages = listOf(systemMessage, userMessageApi)
                    )
                    val response = chatbotApiService.sendMessage(request)
                    val reply = response.choices.firstOrNull()?.message?.content.orEmpty()
                    val assistantMessage = ChatMessage(
                        text = reply,
                        isUser = false,
                        timestamp = getCurrentDateTime()
                    )
                    _messages.value += assistantMessage
                    saveMessage(assistantMessage)
                } else {
                    val errorMessage = ChatMessage(
                        text = "Error: Data pengguna belum lengkap.",
                        isUser = false,
                        timestamp = getCurrentDateTime()
                    )
                    _messages.value += errorMessage
                    saveMessage(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = ChatMessage(
                    text = "Error: ${e.message}",
                    isUser = false,
                    timestamp = getCurrentDateTime()
                )
                _messages.value += errorMessage
                saveMessage(errorMessage)
            }
        }
    }

    private suspend fun saveMessage(message: ChatMessage) {
        chatMessageDao.insertMessage(
            ChatMessageEntity(
                text = message.text,
                isUser = message.isUser,
                timestamp = message.timestamp
            )
        )
    }

    private fun buildUserInfoMessage(
        profile: UserProfile,
        assessment: DetailAssesment,
        history: List<GradeHistory>
    ): String {
        val profileInfo = "Nama: ${profile.name}, Email: ${profile.email}, Gender: ${profile.gender}, Tanggal Lahir: ${profile.dob}, Tempat Lahir: ${profile.pob}, Alamat: ${profile.address}, Nomor Telepon: ${profile.phone_number}, Agama: ${profile.religion}, NISN: ${profile.nisn}, Gaya Belajar: ${profile.learning_style}"

        val assessmentInfo = "Evaluasi dari guru: ${assessment.evaluation}, Saran dari guru: ${assessment.suggestion}, Waktu: ${assessment.time}"

        val historyInfo = "Jumlah Riwayat: ${history.size}, Riwayat: ${
            history.joinToString("\n") {
                "Judul: ${it.quiz_title}, Nilai: ${it.grade}, Waktu: ${it.submit_time}"
            }
        }"

        return "$profileInfo\n$assessmentInfo\n$historyInfo"
    }
}