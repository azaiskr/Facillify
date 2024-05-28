package com.lidm.facillify.ui.chat

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidm.facillify.data.ChatMessage
import com.lidm.facillify.data.remote.api.ApiConfig
import com.lidm.facillify.data.repository.ThreadRepository
import com.lidm.facillify.di.Inject
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
    val apiServiceMain = ApiConfig.getMainApiService(context)
    val threadRepository = ThreadRepository(apiServiceMain)
//    val viewModelFactory = ViewModelFactory(apiService, threadRepository)
    val chatViewModel: ChatViewModel = viewModel(
        factory = ViewModelFactory(
            Inject.privodeChatAPiService(context),
            Inject.provideThreadRepo(context),
            Inject.provideAuthRepo(context)
        )
    )

    ChatScreen(
        viewModel = chatViewModel
    )
}