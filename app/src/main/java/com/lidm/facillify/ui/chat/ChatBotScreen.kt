package com.lidm.facillify.ui.chat

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.lidm.facillify.data.ChatMessage
import com.lidm.facillify.data.remote.api.ApiConfig
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.viewmodel.ChatViewModel
import com.lidm.facillify.util.getCurrentDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatbotScreen(

) {
    //vm
    //state
    val context = LocalContext.current
    val apiService = ApiConfig.getChatbotApiService(context)
    val viewModelFactory = ViewModelFactory(apiService)
    val chatViewModel = ViewModelProvider(
        LocalContext.current as ComponentActivity,
        viewModelFactory
    ).get(ChatViewModel::class.java)

    ChatScreen(
        viewModel = chatViewModel
    )
}