package com.lidm.facillify.ui.konsultasi

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.R
import com.lidm.facillify.ui.components.InputTextFieldDefault
import com.lidm.facillify.ui.components.MainTopAppBar
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.SecondaryBlue
import com.lidm.facillify.ui.theme.SecondaryRed

@Composable
fun KonsultasiForumScreen() {
    //state
    var isDialogOpen by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedSubject by remember { mutableStateOf("") }
    var konsultasiList by remember { mutableStateOf(emptyList<KonsulDummy>()) }

    konsultasiList = listOf(
        KonsulDummy(
            imagePhotoProfile = painterResource(id = R.drawable.pp_deafult),
            name = "Nama 1",
            date = "Date 1",
            title = "Title 1",
            description = "Description 1",
            totalComment = 5,
            subject = "IPA"
        ),
        KonsulDummy(
            imagePhotoProfile = painterResource(id = R.drawable.pp_deafult),
            name = "Nama 2",
            date = "Date 2",
            title = "Title 2",
            description = "Description 2",
            totalComment = 10,
            subject = "IPS"
        ))

    val filteredList = konsultasiList.filter {
        it.title.contains(searchQuery, ignoreCase = true) &&
                (selectedSubject.isEmpty() || it.subject == selectedSubject)
    }

    val subjects = konsultasiList.map { it.subject }.distinct()

    
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            MainTopAppBar(onBackClick = { /*TODO*/ }, onProfileClick = { /*TODO*/ }, backIcon = true, profileIcon = true, sectionTitle = "Konsultasi")

            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")},
                label = { Text("Cari Pertanyaan") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue,
                    unfocusedBorderColor = SecondaryBlue,
                    focusedLabelColor = Blue,
                    unfocusedLabelColor = SecondaryBlue,
                    focusedLeadingIconColor = Blue,
                    unfocusedLeadingIconColor = SecondaryBlue,
                    disabledLabelColor = SecondaryBlue,
                ),
                shape = RoundedCornerShape(16.dp)
            )

            // Chip board for subjects
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                subjects.forEach { subject ->
                    FilterChip(
                        selected = selectedSubject == subject,
                        onClick = {
                            selectedSubject = if (selectedSubject == subject) "" else subject
                        },
                        label = { Text(text = subject, color = if (selectedSubject == subject) DarkBlue else Blue) },
                        colors = FilterChipDefaults.filterChipColors(
                            disabledContainerColor = SecondaryBlue,
                            selectedContainerColor = SecondaryBlue,
                        ),
                        border = if (selectedSubject == subject) {
                            null
                        } else BorderStroke(1.dp, Blue),
                    )
                }
            }

            LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                items(filteredList.size) { index ->
                    val item = filteredList[index]
                    CardKonsultasi(
                        imagePhotoProfile = item.imagePhotoProfile,
                        name = item.name,
                        date = item.date,
                        title = item.title,
                        description = item.description,
                        totalComent = item.totalComment,
                        subject = item.subject,
                        onClickChat = { /*TODO*/ }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        FloatingActionButton(onClick = { isDialogOpen = true },
            containerColor = Blue, modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)){

            Image(imageVector = Icons.Default.Add, contentDescription = "add icon", colorFilter = ColorFilter.tint(Color.White))

        }

        if (isDialogOpen) {
            DialogAddKonsultasi(
                onDismiss = { isDialogOpen = false },
                onConfirm = {
                    // Lakukan aksi konfirmasi di sini
                    //ADD LOGIC
                    isDialogOpen = false
                }
            )
        }

    }
}

@Composable
fun CardKonsultasi(
    imagePhotoProfile: Painter,
    name: String,
    date: String,
    title: String,
    description: String,
    totalComent: Int,
    subject: String,
    onClickChat: () -> Unit,
){
    Card(
        modifier = Modifier.clickable { onClickChat() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Image(
                    painter = imagePhotoProfile,
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
                        color = Color.Black,
                        fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = date,
                        fontSize = 14.sp,
                        color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = title,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                maxLines = 2)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Black,
                maxLines = 3)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { /*TODO*/ }, enabled = false, colors = ButtonDefaults.buttonColors(disabledContainerColor = SecondaryBlue)) {
                    Text(text = subject, color = Blue, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                Text(text = "$totalComent Jawaban", color = Blue, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

    }
}

@Composable
fun DialogAddKonsultasi(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var judulPertanyaan by remember { mutableStateOf("") }
    var deskripsiPertanyaan by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }

    AlertDialog(
        containerColor = Color.White,
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        dismissButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = SecondaryRed)) {
                Text("Batal")
            }
        },
        confirmButton = {
            Button(onClick = onConfirm, colors = ButtonDefaults.buttonColors(containerColor = Blue)) {
                Text("Kirim")
            }
        },
        text = {
            Column {
                InputTextFieldDefault(topText = "Mata Pelajaran", insideText = "Masukan mata pelajaran", valueText = subject, onValueChange = {subject = it} )

                Spacer(modifier = Modifier.height(16.dp))

                InputTextFieldDefault(topText = "Judul pertanyaan", insideText = "Masukan judul pertanyaan", valueText = judulPertanyaan, onValueChange = {judulPertanyaan = it} )

                Spacer(modifier = Modifier.height(16.dp))

                InputTextFieldDefault(topText = "Deskripsi lengkap pertanyaan", insideText = "Masukan penjelasan lebih lengkapnya", valueText = deskripsiPertanyaan, onValueChange = {deskripsiPertanyaan = it} )
            }

        }

    )
}

@Composable
@Preview(showBackground = true)
fun KonsultasiForumScreenPreview() {
    KonsultasiForumScreen()
}

@Composable
@Preview(showBackground = true)
fun CardKonsultasiPreview() {
    CardKonsultasi(
        imagePhotoProfile = painterResource(id = R.drawable.pp_deafult),
        name = "Nama",
        date = "Date",
        title = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        totalComent = 10,
        subject = "IPA",
        onClickChat = {}
    )
}

@Composable
@Preview(showBackground = true)
fun DialogAddKonsultasiPreview() {
    DialogAddKonsultasi(
        onDismiss = { /*TODO*/ },
        onConfirm = { /*TODO*/ }
    )
}

data class KonsulDummy(
    val imagePhotoProfile: Painter,
    val name: String,
    val date: String,
    val title: String,
    val description: String,
    val totalComment: Int,
    val subject: String
)

