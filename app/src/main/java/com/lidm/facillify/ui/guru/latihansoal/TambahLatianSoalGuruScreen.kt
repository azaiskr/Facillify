package com.lidm.facillify.ui.guru.latihansoal

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidm.facillify.data.remote.request.CreateQuizRequest
import com.lidm.facillify.data.remote.request.Question
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.InputTextFieldDefault
import com.lidm.facillify.ui.components.ItemSoalCard
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.SecondaryBlue
import com.lidm.facillify.ui.theme.SecondaryRed
import com.lidm.facillify.ui.viewmodel.TambahLatianSoalGuruViewModel
import com.lidm.facillify.util.ResponseState
import kotlinx.coroutines.launch

@Composable
fun TambahLatianSoalGuruScreen(
    context: Context = LocalContext.current,
    onBackClick: () -> Unit = {}
) {
    //viewmodel
    val tambahSoalViewModel: TambahLatianSoalGuruViewModel = viewModel(factory = ViewModelFactory.getInstance(context.applicationContext))

    val latihanSoal by tambahSoalViewModel.latihanSoal.collectAsState()
    val judulSoal by tambahSoalViewModel.judulSoal.collectAsState()
    val deskripsiSoal by tambahSoalViewModel.deskripsiSoal.collectAsState()
    val durasi by tambahSoalViewModel.durasi.collectAsState()
    val createdQuiz by tambahSoalViewModel.createdQuiz.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var showDialog by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            ) {

                InputTextFieldDefault(topText = "Judul Latihan Soal", insideText = "Masukan judul latihan soal", valueText = judulSoal, onValueChange = {tambahSoalViewModel.setJudulSoal(it)} )

                Spacer(modifier = Modifier.height(16.dp))

                InputTextFieldDefault(topText = "Deskripsi Materi Latihan Soal", insideText = "Masukan deskripsi materi latihan soal", valueText = deskripsiSoal, onValueChange = {tambahSoalViewModel.setDeskripsiSoal(it)} )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn{
                    items(latihanSoal.size){index ->
                        ItemSoalCard(
                            soal = latihanSoal[index].question,
                            jawaban = latihanSoal[index].options,
                            jawabanBenar = latihanSoal[index].correct_option_key,
                            onEditClick = { /*TODO*/ }) {
                            //TODO LOGIC FOR DELETE ITEM AND EDIT
                            tambahSoalViewModel.removeQuestion(index)
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
            ButtonAddSoal (
                onClickAdd = { showDialog = true }
            )
            TimeAndUploadButton(
                modifier = Modifier
                    .padding(horizontal= 16.dp),
                duration = durasi,
                onClickUpload = {
                    val quiz = CreateQuizRequest(
                        title = judulSoal,
                        description = deskripsiSoal,
                        questions = latihanSoal,
                        time = durasi
                    )
                    tambahSoalViewModel.createQuiz(quiz)
                },
                durationOnChange = { tambahSoalViewModel.setDurasi(it.toInt() ?: 0) }
            )
        }
    }
    if (showDialog) {
        DialogTambahSoal(
            onDismissRequest = { showDialog = false },
            onConfirmation = { question ->
                tambahSoalViewModel.addQuestion(question)
                showDialog = false
            }
        )
    }

    LaunchedEffect(createdQuiz) {
        when (createdQuiz) {
            is ResponseState.Success -> {
                coroutineScope.launch {
                    // Show success message
                    //ScaffoldState.snackbarHostState.showSnackbar("Quiz berhasil diunggah")
                    Toast.makeText(context, "Quiz berhasil diunggah.", Toast.LENGTH_SHORT).show()
                    onBackClick()
                }
            }
            is ResponseState.Error -> {
                coroutineScope.launch {
                    // Show error message

                    //Sca.snackbarHostState.showSnackbar("Gagal mengunggah quiz: ${(createdQuiz as ResponseState.Error).error}")
                    Toast.makeText(context, "Quiz gagal diunggah.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {}
        }
    }
}

@Composable
fun TimeAndUploadButton(
    modifier: Modifier = Modifier,
    onClickUpload: () -> Unit,
    duration: Int,
    durationOnChange: (Int) -> Unit
) {
    var durationText by remember { mutableStateOf(if (duration == 0) "" else duration.toString()) }
    var isError by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
    ) {
        Column(
            modifier = Modifier,
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Waktu Pengerjaan", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Blue)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Blue,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //INPUT FIELD
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    value = if (duration == 0) "" else duration.toString(),
                    onValueChange = {
                        durationText = it
                        val intValue = it.toIntOrNull()
                        if (intValue != null) {
                            isError = false
                            durationOnChange(intValue)
                        } else {
                            isError = true
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedTextColor = if (isError) Color.Red else Blue,
                        unfocusedTextColor = if (isError) Color.Red else Blue,
                        focusedBorderColor = if (isError) Color.Red else Color.Transparent,
                        unfocusedBorderColor = if (isError) Color.Red else Color.Transparent
                    ), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = {
                    Text(text = "Dalam Menit", fontSize = 16.sp, color = SecondaryBlue)
                }, shape = RoundedCornerShape(8.dp, 0.dp, 0.dp, 8.dp),
                    isError = isError
                )

                //BUTTON
                Button(
                    onClick = { onClickUpload() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Blue
                    ),
                    shape = RoundedCornerShape(0.dp, 8.dp, 8.dp, 0.dp),
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth(1f)
                )
                    {
                    Text(text = "Unggah", fontSize = 16.sp)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ButtonAddSoal(
    onClickAdd: () -> Unit
){
    Button(onClick = {onClickAdd()}, modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp, 8.dp, 16.dp, 0.dp), colors = ButtonDefaults.buttonColors(containerColor = SecondaryBlue), shape = RoundedCornerShape(8.dp)) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "add soal", tint = Blue )
    }
}

//Dialog Tambah Soal
/*@Composable
fun DialogTambahSoal(
    onDismissRequest: () -> Unit,
    onConfirmation: (Question) -> Unit,
){
    var question by remember { mutableStateOf("") }
    var answers by remember { mutableStateOf(listOf("", "", "", "")) }
    var correctAnswer by remember { mutableIntStateOf(0) }

    AlertDialog(
        containerColor =  Color.White,
        onDismissRequest = { onDismissRequest() },
        dismissButton = {
            Button(onClick = onDismissRequest, colors = ButtonDefaults.buttonColors(containerColor = SecondaryRed)) {
                Text("Batal")
            }
        },
        confirmButton = { Button(
            onClick = {
                //TODO LOGIC FOR ADD ITEM
                onConfirmation(
                    Question(
                        question = question,
                        options = Options(
                            a = answers[0],
                            b = answers[1],
                            c = answers[2],
                            d = answers[3],
                            e = answers[4]
                        ),
                        correct_option_key = listOf("a", "b", "c", "d", "e")[correctAnswer]
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(containerColor = Blue),
        ) {
            Text("Simpan")
        } },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(
                    text = "Soal Pertanyaan",
                    fontSize = 16.sp,
                    color = Blue,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = question,
                    onValueChange = {question = it},
                    placeholder = {
                        Text(
                            text = "Masukan soal pertanyaan",
                            color = SecondaryBlue,
                            fontSize = 16.sp,
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Blue,
                        unfocusedBorderColor = SecondaryBlue,
                        focusedTextColor = Blue,
                        unfocusedTextColor = Blue
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                //answer
                answers.forEachIndexed { index, answer ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RadioButton(
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Blue,
                                unselectedColor = SecondaryBlue
                            ),
                            selected = correctAnswer == index,
                            onClick = { correctAnswer = index}
                        )

                        OutlinedTextField(
                            value = answer,
                            onValueChange = {
                                answers = answers.toMutableList().apply { set(index, it) }
                            },
                            label = {
                                Text("Jawaban ${index + 1}",
                                    color = Blue,)
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Blue,
                                unfocusedBorderColor = SecondaryBlue,
                                focusedTextColor = Blue,
                                unfocusedTextColor = Blue
                            )
                        )

                        if (index == answers.lastIndex && answers.size < 5) {
                            IconButton(onClick = {
                                answers = answers.toMutableList().apply { add("") }
                            }) {
                                Icon(Icons.Default.Add, contentDescription = "Tambah Jawaban", tint = Blue)
                            }
                        } else if (answers.size > 4) {
                            IconButton(onClick = {
                                answers = answers.toMutableList().apply { removeAt(index) }
                                if (correctAnswer == index) {
                                    correctAnswer = 0
                                } else if (correctAnswer > index) {
                                    correctAnswer--
                                }
                            }) {
                                Icon(Icons.Default.Remove, contentDescription = "Hapus Jawaban", tint = AlertRed)
                            }
                        }
                    }
                }
            }
        }
    )
}*/

@Composable
fun DialogTambahSoal(
    onDismissRequest: () -> Unit,
    onConfirmation: (Question) -> Unit,
    context: Context = LocalContext.current
) {
    var question by remember { mutableStateOf("") }
    var answers by remember { mutableStateOf(listOf("", "", "", "", "")) }
    var correctAnswer by remember { mutableIntStateOf(0) }

    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = { onDismissRequest() },
        dismissButton = {
            Button(onClick = onDismissRequest, colors = ButtonDefaults.buttonColors(containerColor = SecondaryRed)) {
                Text("Batal")
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (answers.all { it.isNotEmpty() }) {
                        onConfirmation(
                            Question(
                                question = question,
                                options = answers,
                                correct_option_key = listOf("a", "b", "c", "d", "e")[correctAnswer]
                            )
                        )
                    } else {
                        // Handle the case when not all answers are filled
                        // For example, show a Toast message
                        Toast.makeText(context, "Semua jawaban harus diisi.", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Blue),
            ) {
                Text("Simpan")
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(
                    text = "Soal Pertanyaan",
                    fontSize = 16.sp,
                    color = Blue,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = question,
                    onValueChange = { question = it },
                    placeholder = {
                        Text(
                            text = "Masukan soal pertanyaan",
                            color = SecondaryBlue,
                            fontSize = 16.sp,
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Blue,
                        unfocusedBorderColor = SecondaryBlue,
                        focusedTextColor = Blue,
                        unfocusedTextColor = Blue
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Answer fields
                answers.forEachIndexed { index, answer ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RadioButton(
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Blue,
                                unselectedColor = SecondaryBlue
                            ),
                            selected = correctAnswer == index,
                            onClick = { correctAnswer = index }
                        )

                        OutlinedTextField(
                            value = answer,
                            onValueChange = {
                                answers = answers.toMutableList().apply { set(index, it) }
                            },
                            label = {
                                Text("Jawaban ${index + 1}",
                                    color = Blue,)
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Blue,
                                unfocusedBorderColor = SecondaryBlue,
                                focusedTextColor = Blue,
                                unfocusedTextColor = Blue
                            )
                        )
                    }
                }
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun TambahLationSoalGuruScreenPreview() {
    TambahLatianSoalGuruScreen()
}

@Composable
@Preview(showBackground = true)
fun TimeAndUploadButtonPreview() {
    val duration by remember { mutableIntStateOf(0) }
    TimeAndUploadButton(
        duration = duration,
        onClickUpload = { /*TODO*/ },
        durationOnChange = { /*TODO*/ }
    )
}

@Composable
@Preview(showBackground = true)
fun ButtonAddPreview(){
    ButtonAddSoal(
        onClickAdd = { /*TODO*/ }
    )
}

@Composable
@Preview(showBackground = true)
fun DialogTambahSoalPreview(){
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        DialogTambahSoal(
            onDismissRequest = { /*TODO*/ },
            onConfirmation = { /*TODO*/ }
        )
    }
}
