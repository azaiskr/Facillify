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
        loadMessagesFromDatabase()
    }

    fun deleteMessage(message: ChatMessage) {
        viewModelScope.launch {
            chatMessageDao.deleteMessage(
                ChatMessageEntity(
                    text = message.text,
                    isUser = message.isUser,
                    timestamp = message.timestamp
                )
            )
            // Perbarui state _messages
            _messages.value = _messages.value.filter { it != message }
        }
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

                    //USING PROMPT FRAMEWORK RACE (Role,Action,Context,Execution)
                    val systemMessage = com.lidm.facillify.data.remote.response.Message(
                        role = "system",
                        content = "Anda adalah FACILLIFY AI, chatbot dari aplikasi Learning Management System (LMS) Facillify yang membantu siswa dengan memberikan informasi dan saran pendidikan.\n Berikut adalah informasi tentang pengguna yang sedang berinteraksi dengan Anda:\n$userInfoMessageContent"
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

    private fun loadMessagesFromDatabase() {
        viewModelScope.launch {
            val messagesFromDb = chatMessageDao.getAllMessages().map {
                ChatMessage(
                    text = it.text,
                    isUser = it.isUser,
                    timestamp = it.timestamp
                )
            }
            _messages.value = messagesFromDb
        }
    }


    private fun buildUserInfoMessage(
        profile: UserProfile,
        assessment: DetailAssesment,
        history: List<GradeHistory>
    ): String {
        val profileInfo = "Nama: ${profile.name}\n Email: ${profile.email}\n Gender: ${profile.gender}\n Tanggal Lahir: ${profile.dob}\n Tempat Lahir: ${profile.pob}\n Alamat: ${profile.address}\n Nomor Telepon: ${profile.phone_number}\n Agama: ${profile.religion}\n NISN: ${profile.nisn}\n Gaya Belajar: ${profile.learning_style}\n"

        val assessmentInfo = "Evaluasi dari guru: ${assessment.evaluation}\n Saran dari guru: ${assessment.suggestion}\n Waktu: ${assessment.time}\n"

        val historyInfo = "Jumlah Riwayat: ${history.size}\n Riwayat: ${
            history.joinToString("\n") {
                "Judul: ${it.quiz_title}, Nilai: ${it.grade}, Waktu: ${it.submit_time}\n"
            }
        }"
        //Action, Context, Execution
        return "Anda sedang berinteraksi dengan ${profile.name}, seorang siswa/siswi ${profile.gender} yang memiliki gaya belajar ${profile.learning_style}.\nBerikut adalah informasi tentang ${profile.name} yang dapat membantu Anda memberikan respon yang sesuai dan bermanfaat:\n$profileInfo\n$assessmentInfo\n$historyInfo\nJawaban harus relevan dengan pertanyaan ${profile.name}, mempertimbangkan gaya belajar ${profile.learning_style}nya, menggunakan bahasa yang sopan dan ramah, serta apabila bertanya mengenai soal bentuk matematis jangan berikan dalam tulisan yang dipahami komputer."
    }
}