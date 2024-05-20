package com.lidm.facillify.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.data.ChatMessage
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.Grey
import com.lidm.facillify.ui.theme.SecondaryBlue

@Composable
fun ChatBubble(message: ChatMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (message.isUser) SecondaryBlue else Grey,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = message.text,
                color = Black,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ChatBubblePreview() {
    ChatBubble(
        message = ChatMessage(
            text = "Halo, apa kabar?",
            isUser = true,
            timestamp = "2131"
        )
    )
}