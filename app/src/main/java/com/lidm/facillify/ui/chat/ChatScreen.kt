package com.lidm.facillify.ui.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidm.facillify.data.ChatMessage
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.ChatBubble
import com.lidm.facillify.ui.components.ChatInputField
import com.lidm.facillify.ui.components.DateHeader
import com.lidm.facillify.ui.viewmodel.ChatViewModel
import com.lidm.facillify.util.getCurrentDateTime
import com.lidm.facillify.util.getYesterdayDateString

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    messages: List<ChatMessage>
) {
    var userInput by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    var showDialog by remember { mutableStateOf(false) }
    var messageToDelete by remember { mutableStateOf<ChatMessage?>(null) }

    LaunchedEffect(messages.size) {
        listState.animateScrollToItem(messages.size)
    }

    if (showDialog) {
        ConfirmDeleteDialog(
            message = messageToDelete,
            onConfirm = {
                viewModel.deleteMessage(it)
                showDialog = false
                messageToDelete = null
            },
            onDismiss = {
                showDialog = false
                messageToDelete = null
            }
        )
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
            var lastDate: String? = null
            items(messages) { message ->
                val messageDate = message.timestamp
                if (messageDate != lastDate) {
                    lastDate = messageDate
                    val headerText = when (messageDate) {
                        getCurrentDateTime() -> "Hari Ini"
                        getYesterdayDateString() -> "Kemarin"
                        else -> messageDate
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        DateHeader(date = headerText)
                    }
                }
                ChatBubble(
                    message = message,
                    onLongPress = {
                        messageToDelete = it
                        showDialog = true
                    }
                )
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

@Composable
fun ConfirmDeleteDialog(
    message: ChatMessage?,
    onConfirm: (ChatMessage) -> Unit,
    onDismiss: () -> Unit
) {
    if (message != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Konfirmasi Hapus") },
            text = { Text(text = "Apakah Anda yakin ingin menghapus pesan ini?") },
            confirmButton = {
                Button(onClick = { onConfirm(message) }) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Batal")
                }
            }
        )
    }
}