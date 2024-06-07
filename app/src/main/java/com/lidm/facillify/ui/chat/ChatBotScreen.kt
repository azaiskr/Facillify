package com.lidm.facillify.ui.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.responseStateScreen.ErrorScreen
import com.lidm.facillify.ui.responseStateScreen.LoadingScreen
import com.lidm.facillify.ui.viewmodel.ChatViewModel
import com.lidm.facillify.util.ResponseState
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatbotScreen() {
    val context = LocalContext.current
    val chatViewModel: ChatViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context.applicationContext)
    )

    val messages by chatViewModel.messages.collectAsState()
    val profileState by chatViewModel.profileSiswa.collectAsState()
    val assessmentState by chatViewModel.assessmentSiswa.collectAsState()
    val historyState by chatViewModel.historySiswa.collectAsState()

    if (profileState is ResponseState.Success && assessmentState is ResponseState.Success && historyState is ResponseState.Success) {
        ChatScreen(
            viewModel = chatViewModel,
            messages = messages
        )
    } else if (profileState is ResponseState.Error || assessmentState is ResponseState.Error || historyState is ResponseState.Error) {
        Text(text = "Error: Data pengguna belum lengkap atau terjadi kesalahan.", modifier = Modifier.fillMaxSize())
    } else {
        LoadingScreen()
    }

}
