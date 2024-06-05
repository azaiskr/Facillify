package com.lidm.facillify.ui.siswa

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidm.facillify.R
import com.lidm.facillify.data.local.MateriBelajar
import com.lidm.facillify.ui.components.CardLatihanItem
import com.lidm.facillify.ui.siswa.belajar.MateriBelajarItem
import com.lidm.facillify.data.remote.response.MaterialList
import com.lidm.facillify.data.remote.response.QuizListItem
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.DialogConfirm
import com.lidm.facillify.ui.responseStateScreen.ErrorScreen
import com.lidm.facillify.ui.responseStateScreen.LoadingScreen
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.viewmodel.HomeSiswaViewModel
import com.lidm.facillify.util.ResponseState

@Composable
fun SiswaHomeScreen(
    modifier: Modifier = Modifier,
    onItemBelajarClick: (String) -> Unit,
    onItemLatihanClick: (String) -> Unit,
    onNavigateToBelajar: () -> Unit,
    onNavigateToLatihan: () -> Unit,
    onNavigateToChatbot: () -> Unit,
    onNavigateToTestGayaBelajar: () -> Unit
) {
    val viewModel: HomeSiswaViewModel = viewModel(
        factory = ViewModelFactory.getInstance(
            LocalContext.current)
    )
    val quizList by viewModel.quizList.collectAsState()
    val materialResponse by viewModel.materialResponse.collectAsState()
    var listOfQuiz by rememberSaveable {
        mutableStateOf<List<QuizListItem>>(emptyList())
    }

    LaunchedEffect(Unit) {
        viewModel.getQuizList()
        viewModel.getMaterial()
    }

    when (quizList) {
        is ResponseState.Loading -> {
            LoadingScreen()

        }
        is ResponseState.Success -> {
            val quizResponse = (quizList as ResponseState.Success).data
            listOfQuiz = quizResponse.result

            when(materialResponse){
                is ResponseState.Loading -> {
                    LoadingScreen()
                }
                is ResponseState.Success -> {
                    val materialList = (materialResponse as ResponseState.Success).data
                    HomeScreenContent(
                        modifier = modifier,
                        onItemBelajarClick = onItemBelajarClick,
                        onItemLatihanClick = onItemLatihanClick,
                        onNavigateToBelajar = onNavigateToBelajar,
                        onNavigateToLatihan = onNavigateToLatihan,
                        onNavigateToChatbot = onNavigateToChatbot,
                        listQuiz = listOfQuiz,
                        listMaterial = materialList
                    )
                }
                is ResponseState.Error -> {
                    ErrorScreen (
                        onTryAgain = { viewModel.getMaterial() }
                    )
                }
            }
        }
        is ResponseState.Error -> {
            ErrorScreen (
                onTryAgain = { viewModel.getQuizList() }
            )
        }
    }

    HomeScreenContent(
        modifier = modifier,
        onItemBelajarClick = onItemBelajarClick,
        onItemLatihanClick = onItemLatihanClick,
        onNavigateToBelajar = onNavigateToBelajar,
        onNavigateToLatihan = onNavigateToLatihan,
        onNavigateToChatbot = onNavigateToChatbot,
        onNavigateToTestGayaBelajar = onNavigateToTestGayaBelajar,
        listQuiz = listOfQuiz
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    onItemBelajarClick: (String) -> Unit = {},
    onItemLatihanClick: (String) -> Unit = {},
    onNavigateToBelajar: () -> Unit = {},
    onNavigateToLatihan: () -> Unit = {},
    onNavigateToChatbot: () -> Unit = {},
    onNavigateToTestGayaBelajar: () -> Unit = {},
    listQuiz: List<QuizListItem>
    listMaterial: List<MateriBelajar>
) {
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var selectedLatihanId by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Button(
            onClick = { onNavigateToChatbot() },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
                .height(56.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Blue, Black
                        ),
                    ),
                    shape = RoundedCornerShape(16.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                Color.Transparent,
                contentColor = Color.White
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier.padding(vertical = 4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.chatbot),
                    contentDescription = null,
                    modifier = modifier.size(32.dp)
                )
                Text(
                    text = "Tanya AI",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Button(
            onClick = { onNavigateToTestGayaBelajar() },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
                .height(56.dp)
                .border( 1.dp, Blue, shape = RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(
                Color.Transparent,
                contentColor = Color.White
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier.padding(vertical = 4.dp)
            ) {
                Text(
                    text = "Test Gaya Belajar",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = Blue
                )
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Belajar",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Black,
                            color = Blue,
                            modifier = modifier.align(Alignment.CenterStart)
                        )
                        TextButton(
                            onClick = { onNavigateToBelajar() },
                            modifier = modifier.align(Alignment.CenterEnd)
                        ) {
                            Text(
                                text = "Selengkapnya",
                                style = MaterialTheme.typography.labelSmall,
                                color = Blue,
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                                contentDescription = null,
                                tint = Blue,
                                modifier = modifier
                                    .padding(start = 8.dp)
                                    .size(16.dp)
                            )
                        }
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(listMaterial.size) { index ->
                            MateriBelajarItem(
                                modifier = modifier,
                                onClick = { onItemBelajarClick(listMaterial[index].id) },
                                materi = listMaterial[index]
                            )
                        }
                    }
                }
            }
            item {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Latihan Soal",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Black,
                        color = Blue,
                        modifier = modifier.align(Alignment.CenterStart)
                    )
                    TextButton(
                        onClick = { onNavigateToLatihan() },
                        modifier = modifier.align(Alignment.CenterEnd)
                    ) {
                        Text(
                            text = "Selengkapnya",
                            style = MaterialTheme.typography.labelSmall,
                            color = Blue,
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                            contentDescription = null,
                            tint = Blue,
                            modifier = modifier
                                .padding(start = 8.dp)
                                .size(16.dp)
                        )
                    }
                }
            }
            items(listQuiz.size) {
                Box(modifier = modifier.padding(horizontal = 16.dp)) {
                    CardLatihanItem(
                        latihan = listQuiz[it],
                        modifier = modifier,
                        onCLick = {
                            selectedLatihanId = listQuiz[it].id
                            showDialog = true
                        }
                    )
                }
            }
        }

        if (showDialog && selectedLatihanId != null) {
            DialogConfirm(
                onDismiss = { showDialog = false },
                onConfirm = {
                    showDialog = false
                    onItemLatihanClick(selectedLatihanId!!)
                },
                title = "Kerjakan Latihan?",
                msg = "Yakin ingin mengerjakan latihan ini? Kamu tidak akan dapat kembali ketika latihan dimulai. Pastikan kamu sudah siap ya!",
                confirmLabel = "Kerjakan",
                dismissLabel = "Kembali"
            )
        }

    }
}
