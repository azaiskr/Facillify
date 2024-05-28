package com.lidm.facillify.ui.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.lidm.facillify.data.remote.api.ApiConfig
import com.lidm.facillify.data.repository.ThreadRepository
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.ChatBubble
import com.lidm.facillify.ui.components.ChatInputField
import com.lidm.facillify.ui.components.DateHeader
import com.lidm.facillify.ui.viewmodel.ChatViewModel
import com.lidm.facillify.util.formatDate
import com.lidm.facillify.util.getCurrentDate
import com.lidm.facillify.util.getYesterdayDate
import com.lidm.facillify.util.parseDate
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel
) {
    var userInput by remember { mutableStateOf("") }
    val messages by viewModel.messages.collectAsState()
    // Create a reference to the LazyListState
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        // Scroll to the bottom when a new message is added
        listState.animateScrollToItem(messages.size)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            state = listState
        ) {
            var lastDate: LocalDate? = null
            items(messages) { message ->
                val messageDate = parseDate(message.timestamp)
                if (messageDate != lastDate) {
                    lastDate = messageDate
                    val headerText = when (messageDate) {
                        getCurrentDate() -> "Hari Ini"
                        getYesterdayDate() -> "Kemarin"
                        else -> formatDate(messageDate)
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        DateHeader(date = headerText)
                    }
                }
                ChatBubble(message = message)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 16.dp)
        ) {
            ChatInputField(
                message = userInput,
                onMessageChange = { userInput = it },
                onSend = {
                    if (userInput.isNotEmpty()) {
                        viewModel.sendMessage(userInput)
                        userInput = ""
                    }
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun ChatScreenPreview() {
    val context = LocalContext.current
    val apiServiceChatBot = ApiConfig.getChatbotApiService(context)
    val apiServiceMain = ApiConfig.getMainApiService(context)
    val threadRepository = ThreadRepository(apiServiceMain)
    val viewModelFactory = ViewModelFactory(apiServiceChatBot, threadRepository)
    val chatViewModel = ViewModelProvider(
        LocalContext.current as androidx.activity.ComponentActivity,
        viewModelFactory
    )[ChatViewModel::class.java]

    ChatScreen(viewModel = chatViewModel)
}