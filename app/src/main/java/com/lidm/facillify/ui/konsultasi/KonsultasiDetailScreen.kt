package com.lidm.facillify.ui.konsultasi

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.lidm.facillify.R
import com.lidm.facillify.data.remote.request.CreateCommentThreadRequest
import com.lidm.facillify.data.remote.response.ThreadDetailResponse
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.ChatInputField
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.SecondaryBlue
import com.lidm.facillify.ui.viewmodel.ThreadViewModel
import com.lidm.facillify.util.ResponseState
import com.lidm.facillify.util.convertToReadableDate

@Composable
fun KonsultasiDetailScreen(
    threadID: String,
    context: Context = LocalContext.current
) {
    //viewmodel
    val threadViewModel: ThreadViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context.applicationContext)
    )

    //state
    val threadDetailState by threadViewModel.threadDetail.collectAsState()
    val swipeRefreshState = remember { SwipeRefreshState(isRefreshing = false) }
    val createdCommentResult by threadViewModel.createCommentResult.collectAsState()
    val dateThread by threadViewModel.dateThread.collectAsState()
    val subjectThread by threadViewModel.subjectThread.collectAsState()

    var jawaban by remember { mutableStateOf("") }

    val emailUserState by threadViewModel.emailUser.collectAsState()

    val emailUser = if (emailUserState is ResponseState.Success) {
        (emailUserState as ResponseState.Success).data
    } else {
        Log.e( "KonsultasiDetailScreen", "Failed to fetch email user")// Default value or handle loading/error state
    }

    val currentSubject = if (subjectThread is ResponseState.Success) {
        (subjectThread as ResponseState.Success).data
    } else {
        Log.e( "KonsultasiDetailScreen", "Failed to fetch subject thread")// Default value or handle loading/error state
    }

    val currentDate = if (dateThread is ResponseState.Success) {
        (dateThread as ResponseState.Success).data
    } else {
        Log.e( "KonsultasiDetailScreen", "Failed to fetch date thread")// Default value or handle loading/error state
        null
    }

    //launcheffect
    LaunchedEffect(Unit) {
        threadViewModel.getThreadDetail(threadID)
        threadViewModel.getEmailUser()
        threadViewModel.getDateThread(threadID)
        threadViewModel.getSubjectThread(threadID)
    }

    LaunchedEffect(createdCommentResult) {
        when (createdCommentResult) {
            is ResponseState.Success -> {
                Toast.makeText(context, "Komentar berhasil dikirim", Toast.LENGTH_SHORT).show()
                threadViewModel.resetCreateCommentResult()
                threadViewModel.getThreadDetail(threadID)
            }
            is ResponseState.Error -> {
                Toast.makeText(context, "Gagal mengirim komentar: ${(createdCommentResult as ResponseState.Error).error}", Toast.LENGTH_LONG).show()
                threadViewModel.resetCreateCommentResult()
            }
            else -> {}
        }
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (threadDetailState) {
            is ResponseState.Loading -> {
                //Loading Indicator
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is ResponseState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${(threadDetailState as ResponseState.Error).error}")
                }
                Log.e("KonsultasiDetailForumScreen", "Error: $threadDetailState")
            }

            is ResponseState.Success -> {
                val threadDetail = (threadDetailState as ResponseState.Success<ThreadDetailResponse>).data

                SwipeRefresh(state = swipeRefreshState, onRefresh = { threadViewModel.getThreadDetail(threadID) }) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 80.dp)
                        ) {
                            item {
                                val readbleDateMainContent = convertToReadableDate(currentDate.toString())
                                Log.d("KonsultasiDetailScreen", "readbleDateMainContent: $readbleDateMainContent")
                                // Main Content
                                MainContentKonsulDetail(
                                    photoProfil = painterResource(id = R.drawable.pp_deafult), //TODO: change with real data
                                    name = threadDetail.op_name,
                                    date = readbleDateMainContent ,
                                    title = threadDetail.title,
                                    description = threadDetail.content,
                                    subject = currentSubject.toString()
                                )
                                HorizontalDivider()
                            }
                            threadDetail.comments.let { comments ->
                                if (comments.isNotEmpty()) {
                                    items(comments.size) { index ->
                                        val comment = comments[index]
                                        Log.d("KonsultasiDetailScreen", "comment index: ${comments.size}")
                                        Log.d("KonsultasiDetailScreen", "Total comment: $comment")
                                        if (comment.email == null && comment.content == null && comment.time == null && comments.size == 1){
                                            Box(modifier = Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
                                                Text(text = "Tidak Ada Komentar")
                                            }
                                        } else {
                                            val readableDate = comment.time?.let { convertToReadableDate(it) } ?: "Invalid date"
                                            Log.d("KonsultasiDetailScreen", "readableDate: $readableDate")
                                            CommentSection(
                                                imageUserComment = painterResource(id = R.drawable.pp_deafult), //TODO: replace with actual image data if available
                                                nameUser = comment.email ?: "Unknown",
                                                date = readableDate,
                                                comment = comment.content ?: "No comment"
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    ) {
                        ChatInputField(message = jawaban, onMessageChange = { jawaban = it }, onSend = {
                            Log.d("KonsultasiDetailScreen", "emailUser: $emailUser")
                            threadViewModel.createThreadComment(
                                CreateCommentThreadRequest(
                                    thread_id = threadID,
                                    email = emailUser.toString(), // Use the user email
                                    content = jawaban
                                )
                            )
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun MainContentKonsulDetail(
    photoProfil: Painter,
    name: String,
    date: String,
    title: String,
    description: String,
    subject: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = photoProfil,
                contentDescription = "Photo Profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(48.dp),
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    color = Black,
                    fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = date,
                    fontSize = 14.sp,
                    color = Black )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { /*TODO*/ }, enabled = false, colors = ButtonDefaults.buttonColors(disabledContainerColor = SecondaryBlue)) {
                Text(text = subject, color = Blue, fontSize = 14.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            }
        }
        Text(
            text = title,
            fontSize = 18.sp,
            color = Black,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            fontSize = 16.sp,
            color = Black)

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun CommentSection(
    imageUserComment: Painter,
    nameUser: String,
    date: String,
    comment: String
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Image(painter = imageUserComment, contentDescription = "pp", modifier = Modifier
                .size(32.dp)
                .clip(CircleShape), contentScale = ContentScale.Crop)

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text = nameUser, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Black)

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = comment, fontSize = 16.sp, color = Black)

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = date, fontSize = 12.sp, color = Color.Gray)
            }
        }
        HorizontalDivider()
    }

}