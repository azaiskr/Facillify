package com.lidm.facillify.ui.guru.latihansoal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidm.facillify.data.remote.response.QuizListItem
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.CardLatihanSoalGuru
import com.lidm.facillify.ui.components.DialogConfirm
import com.lidm.facillify.ui.components.ShowToast
import com.lidm.facillify.ui.responseStateScreen.ErrorScreen
import com.lidm.facillify.ui.responseStateScreen.LoadingScreen
import com.lidm.facillify.ui.viewmodel.LatihanSoalGuruViewModel
import com.lidm.facillify.util.ResponseState

@Composable
fun BaseLatihanSoalGuruScreen(

) {
    val viewModel: LatihanSoalGuruViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
    val response by viewModel.quizList.collectAsState()
    val deleteResponse by viewModel.deleteResponse.collectAsState()

    LaunchedEffect(deleteResponse) {
        if (deleteResponse is ResponseState.Success) {
            viewModel.resetDeleteResponse()
            viewModel.getQuizList()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getQuizList()
    }

    when (response) {
        is ResponseState.Loading -> LoadingScreen()
        is ResponseState.Success -> {
            val quizList = (response as ResponseState.Success).data.result
            if (deleteResponse is ResponseState.Success) {
                ShowToast(message = "Item latihan berhasil di hapus")
            }
            LatihanSoalGuruScreen(
                listSoal = quizList,
                onDelete = viewModel::deleteQuiz
            )
        }

        is ResponseState.Error -> {
            ErrorScreen(
                onTryAgain = viewModel::getQuizList
            )
        }
    }

    when (deleteResponse) {
        is ResponseState.Success -> {
            ShowToast(message = "Item latihan berhasil di hapus")
        }
        is ResponseState.Error -> {
            ShowToast(message = "Item latihan gagal di hapus")
        }
        else -> {}
    }
}

@Composable
fun LatihanSoalGuruScreen(
    listSoal: List<QuizListItem>,
    onDelete: (quizId: String) -> Unit
) {
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var selectedLatihanId by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                items(listSoal.size) { index ->
                    CardLatihanSoalGuru(
                        judulSoal = listSoal[index].title,
                        deskripsiSoal = listSoal[index].description ?: "",
                        waktuSoal = listSoal[index].time ?: 0,
                        deleteItem = {
                            showDialog = true
                            selectedLatihanId = listSoal[index].id
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
    if (showDialog && selectedLatihanId != null) {
        DialogConfirm(
            onDismiss = { showDialog = false },
            onConfirm = {
                showDialog = false
                onDelete(selectedLatihanId!!)
            },
            title = "Hapus Latihan?",
            msg = "Apakah Anda yakin inin menghapus item latihan ini?",
            confirmLabel = "Hapus",
            dismissLabel = "Batal"
        )
    }
}