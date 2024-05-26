package com.lidm.facillify.ui.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.lidm.facillify.data.ChatMessage
import com.lidm.facillify.util.getCurrentDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatbotScreen(

) {
    //vm
    //state
    ChatScreen(
        userToChat = "AI", chatMessage = listOf(
            ChatMessage(
                text = "Halo, apa kabar?",
                isUser = true,
                timestamp = getCurrentDateTime()
            )
        )
    )
}