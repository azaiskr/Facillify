package com.lidm.facillify.ui.siswa.belajar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lidm.facillify.data.local.MateriBelajar
import com.lidm.facillify.data.local.listMateri
import com.lidm.facillify.ui.components.SearchAppBar

@Composable
fun MateriBelajarAudioScreen(
    materiId: Int,
    modifier: Modifier,
    onNavigateToAudioPlayer: (Int, String) -> Unit
) {
    val materi = listMateri.find { it.id == materiId }!!
    Column (
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

@Composable
fun ListMateriAudio(
    modifier: Modifier,
    materi: MateriBelajar,
    onNavigateToMateriAudio: (Int, String) -> Unit,
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
