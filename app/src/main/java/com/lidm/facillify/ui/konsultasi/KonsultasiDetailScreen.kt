package com.lidm.facillify.ui.konsultasi

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.R
import com.lidm.facillify.ui.components.ChatInputField
import com.lidm.facillify.ui.components.MainTopAppBar
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.SecondaryBlue

@Composable
fun KonsultasiDetailScreen() {
    var jawaban by remember { mutableStateOf("") }
    var listComment by remember { mutableStateOf<List<dummyCommentKonsul>>(listOf()) }

    listComment = listOf(
        dummyCommentKonsul(
            imageUserComment = painterResource(id = R.drawable.pp_deafult),
            nameUser = "Irwan",
            date = "20 Oktober 2021",
            comment = "KI HAJAR DEWANTO BUKAN GASIH??"
        ),
        dummyCommentKonsul(
            imageUserComment = painterResource(id = R.drawable.pp_deafult),
            nameUser = "Ujang",
            date = "20 Oktober 2021",
            comment = "KI HAJAR DEWANTO BUKAN GASIH??"
        ),
        dummyCommentKonsul(
            imageUserComment = painterResource(id = R.drawable.pp_deafult),
            nameUser = "Jamilah",
            date = "20 Oktober 2021",
            comment = "KI HAJAR DEWANTO BUKAN GASIH??"
        ),
        dummyCommentKonsul(
            imageUserComment = painterResource(id = R.drawable.pp_deafult),
            nameUser = "Suwandi",
            date = "20 Oktober 2021",
            comment = "KI HAJAR DEWANTO BUKAN GASIH??"
        ),
        dummyCommentKonsul(
            imageUserComment = painterResource(id = R.drawable.pp_deafult),
            nameUser = "Irwan",
            date = "20 Oktober 2021",
            comment = "KI HAJAR DEWANTO BUKAN GASIH??"
        ),
        dummyCommentKonsul(
            imageUserComment = painterResource(id = R.drawable.pp_deafult),
            nameUser = "Ujang",
            date = "20 Oktober 2021",
            comment = "KI HAJAR DEWANTO BUKAN GASIH??"
        ),
        dummyCommentKonsul(
            imageUserComment = painterResource(id = R.drawable.pp_deafult),
            nameUser = "Jamilah",
            date = "20 Oktober 2021",
            comment = "KI HAJAR DEWANTO BUKAN GASIH??"
        ),
        dummyCommentKonsul(
            imageUserComment = painterResource(id = R.drawable.pp_deafult),
            nameUser = "Suwandi",
            date = "20 Oktober 2021",
            comment = "KI HAJAR DEWANTO BUKAN GASIH??"
        ),
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            MainTopAppBar(onBackClick = { /*TODO*/ }, onProfileClick = { /*TODO*/ }, backIcon = true, profileIcon = false)

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 80.dp)
            ) {
                item {
                    // Main Content
                    MainContentKonsulDetail(
                        photoProfil = painterResource(id = R.drawable.pp_deafult),
                        name = "Penggor",
                        date = "20 Oktober 2021",
                        title = "Pelajaran Bahasa Indonesia",
                        description = "siapa penemu bahasa indonesia?",
                        subject = "Ilmu Pengetahuan Alamsyah"
                    )
                    HorizontalDivider()
                }
                items(listComment.size) {
                    CommentSection(
                        imageUserComment = listComment[it].imageUserComment,
                        nameUser = listComment[it].nameUser,
                        date = listComment[it].date,
                        comment = listComment[it].comment
                    )
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

            })
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

@Composable
@Preview(showBackground = true)
fun KonsultasiDetailScreenPreview() {
    KonsultasiDetailScreen()
}

@Composable
@Preview(showBackground = true)
fun CommentSectionPreview() {
    CommentSection(
        imageUserComment = painterResource(id = R.drawable.pp_deafult),
        nameUser = "Ujang",
        date = "20 Oktober 2021",
        comment = "KI HAJAR DEWANTO BUKAN GASIH??"
    )
}

data class dummyCommentKonsul(
    val imageUserComment: Painter,
    val nameUser: String,
    val date: String,
    val comment: String
)