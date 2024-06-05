package com.lidm.facillify.ui.siswa.belajar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import com.lidm.facillify.data.local.MateriBelajar
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.SearchAppBar
import com.lidm.facillify.ui.responseStateScreen.ErrorScreen
import com.lidm.facillify.ui.responseStateScreen.LoadingScreen
import com.lidm.facillify.ui.viewmodel.MateriBelajarViewModel
import com.lidm.facillify.util.ResponseState

@Composable
fun MateriBelajarAudioScreen(
    materiId: String,
    modifier: Modifier,
    onNavigateToAudioPlayer: (String, String) -> Unit
) {
    val viewModel: MateriBelajarViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
    val materiDetail by viewModel.materiDetailResponse.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getMaterialDetail(materiId)
    }

    when (materiDetail) {
        is ResponseState.Loading -> LoadingScreen()
        is ResponseState.Success -> {
            val materi = (materiDetail as ResponseState.Success).data ?: return

            Column(
                modifier = modifier
                    .fillMaxSize()
            ) {

                ListMateriAudio(
                    modifier = modifier,
                    materi = materi,
                    onNavigateToMateriAudio = onNavigateToAudioPlayer
                )
            }
        }
        is ResponseState.Error -> {
            ErrorScreen(
                onTryAgain = { viewModel.getMaterialDetail(materiId) }
            )
        }
    }


}

@Composable
fun ListMateriAudio(
    modifier: Modifier,
    materi: MateriBelajar,
    onNavigateToMateriAudio: (String, String) -> Unit,
    isSearchBarVisible: Boolean = true
) {
    var query by rememberSaveable {
        mutableStateOf("")
    }
    var active by rememberSaveable {
        mutableStateOf(false)
    }

    if (isSearchBarVisible) {
        SearchAppBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = { active = false },
            active = false,
            onActiveChange = { active = false },
            label = "Cari audio",
            modifier = modifier
        )
    }

    val filteredMateri = if (query != "") {
        materi.materiAudio.filter { it.title.contains(query, ignoreCase = true) }
    } else {
        materi.materiAudio
    }

    //TODO : AUDIO PLAYER WHEN U HAVE MORE TIME
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(filteredMateri.size) {
            MateriVideoItem(
                modifier = modifier,
                onClick = { onNavigateToMateriAudio(materi.id, filteredMateri[it].id) },
                videoItem = filteredMateri[it],
            )
        }
    }

}
