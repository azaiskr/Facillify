package com.lidm.facillify.ui.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lidm.facillify.data.ChatMessage
import com.lidm.facillify.ui.components.ChatBubble
import com.lidm.facillify.ui.components.ChatInputField
import com.lidm.facillify.ui.components.DateHeader
import com.lidm.facillify.ui.components.MainTopAppBar
import com.lidm.facillify.util.getCurrentDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(
    isBotChat: Boolean = false,
    onBackClick: () -> Unit = {},
    userToChat: String,
    chatMessage: List<ChatMessage>
) {
    var messages by remember { mutableStateOf(chatMessage) }
    var currentMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainTopAppBar(onBackClick = onBackClick, onProfileClick = { /*TODO*/ }, profileIcon = false, backIcon = true, sectionTitle = if (!isBotChat) userToChat else "FACILLIFY AI")

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
        ) {
            val groupedMessages = messages.groupBy { it.timestamp }
            if (groupedMessages.isNotEmpty()) {
                groupedMessages.forEach { (date, messages) ->
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            DateHeader(date = date)
                        }
                    }

                    items(messages) { message ->
                        ChatBubble(message)
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 16.dp)
        ) {
            ChatInputField(
                message = currentMessage,
                onMessageChange = { currentMessage = it },
                onSend = {
                    //TODO: LOGIC FOR SEND MESSAGE
                    if (currentMessage.isNotEmpty()) {
                        messages = messages + ChatMessage(
                            text = currentMessage,
                            isUser = true,
                            timestamp = getCurrentDateTime()
                        )
                        currentMessage = ""
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
    ChatScreen(
        isBotChat = true,
        userToChat = "Budi",
        chatMessage = listOf(
            ChatMessage(
                text = "Halo, apa kabar?",
                isUser = true,
                timestamp = "9 Januari 2024"
            ),
            ChatMessage(
                text = "Halo, baik. Kamu?",
                isUser = false,
                timestamp = "9 Januari 2024"
            ),
            ChatMessage(
                text = "Baik juga",
                isUser = true,
                timestamp = "9 Januari 2024"
            ),
            ChatMessage(
                text = "Halo, apa kabar?",
                isUser = true,
                timestamp = "9 Januari 2024"
            ),
            ChatMessage(
                text = "Halo, baik. Kamu?",
                isUser = false,
                timestamp = "10 Januari 2024"
            ),
            ChatMessage(
                text = "Baik juga",
                isUser = true,
                timestamp = "10 Januari 2024"
            ),
            ChatMessage(
                text = "Halo, apa kabar?",
                isUser = true,
                timestamp = "10 Januari 2024"
            ),
            ChatMessage(
                text = "Halo, baik. Kamu?",
                isUser = false,
                timestamp = "10 Januari 2024"
            ),
            ChatMessage(
                text = "Baik juga",
                isUser = true,
                timestamp = "10 Januari 2024"
            ),
            ChatMessage(
                text = "Halo, apa kabar?",
                isUser = true,
                timestamp = "10 Januari 2024"
            ),
            ChatMessage(
                text = "Halo, baik. Kamu?",
                isUser = false,
                timestamp = "10 Januari 2024"
            ),
            ChatMessage(
                text = "Baik juga",
                isUser = true,
                timestamp = "10 Januari 2024"
            ),
            ChatMessage(
                text = "Halo, apa kabar?",
                isUser = true,
                timestamp = "10 Januari 2024"
            ),
            ChatMessage(
                text = "Halo, baik. Kamu?",
                isUser = false,
                timestamp = "11 Januari 2024"
            ),
            ChatMessage(
                text = "Baik juga",
                isUser = true,
                timestamp = "11 Januari 2024"
            ),
            ChatMessage(
                text = "Halo, apa kabar?",
                isUser = true,
                timestamp = "11 Januari 2024"
            ),
            ChatMessage(
                text = "Halo, baik. Kamu?",
                isUser = false,
                timestamp = "11 Januari 2024"
            )
        )
    )

}