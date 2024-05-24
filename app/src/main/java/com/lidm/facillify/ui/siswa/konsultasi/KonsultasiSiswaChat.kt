package com.lidm.facillify.ui.siswa.konsultasi

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.lidm.facillify.data.ChatMessage
import com.lidm.facillify.ui.chat.ChatScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KonsultasiSiswaChat(

) {
    //        when (val response = viewModel.materiBelajar){
//            is Response.Loading -> {
//                /*TODO*/
//            }
//            is Response.Success -> {
//                /*TODO*/
//            }
//            is Response.Error -> {
//                /*TODO*/
//            }
//        }

    ChatScreen(
        userToChat = "Dr. Ir. H. Fathul Muzakki, M. Sc.",
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
        )
    )
}