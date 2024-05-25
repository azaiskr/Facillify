package com.lidm.facillify.ui.siswa.belajar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lidm.facillify.ui.components.CardLatihanItem
import com.lidm.facillify.ui.components.SearchAppBar
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.OnBlueSecondary
import com.lidm.facillify.util.Question
import com.lidm.facillify.util.dummyQuestions

data class LatihanItem(
    val id: Int,
    val jmlSoal: Int,
    val judul: String,
    val deskripsi: String,
    val waktu: Int,
    val questions: List<Question>,
    val subBab: String,
    val done: Boolean = false,
)

data class Filter(
    val id: Int,
    val name: String
)

val dummyDataLatihan = listOf(
    LatihanItem(
        id = 1,
        jmlSoal = 10,
        waktu = 20,
        judul = "Latihan 1",
        deskripsi = "Latihan 1",
        questions = dummyQuestions,
        subBab = "Bangun Ruang",
        done = true
    ),
    LatihanItem(
        id = 2,
        jmlSoal = 20,
        waktu = 45,
        judul = "Latihan 2",
        questions = dummyQuestions,
        deskripsi = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        subBab = "Bangun Ruang",
        done = false
    ),
    LatihanItem(
        id = 3,
        jmlSoal = 25,
        waktu = 60,
        questions = dummyQuestions,
        judul = "Latihan 3",
        deskripsi = "Latihan 3",
        subBab = "Statistika",
        done = false
    ),
)

val dummyDataFilter = listOf(
    Filter(
        id = 1,
        name = "Selesai"
    ),
    Filter(
        id = 2,
        name = "Bangun Ruang"
    ),
    Filter(
        id = 3,
        name = "Statistika"
    ),
    Filter(
        id = 4,
        name = "SPLDV"
    ),
)

@Composable
fun LatihanSiswaListScreen(
    modifier: Modifier,
    onNavigateToLatihanForm: (Int) -> Unit,
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
    ListLatihan(
        data = dummyDataLatihan,
        filter = dummyDataFilter,
        modifier = modifier,
        onNavigateToLatihanForm = onNavigateToLatihanForm
    )
}

@Composable
fun ListLatihan(
    data: List<LatihanItem>,
    filter : List<Filter>,
    modifier: Modifier,
    onNavigateToLatihanForm: (Int) -> Unit,
) {
    Column (
        modifier = modifier
            .fillMaxSize()
    ) {
        var query by rememberSaveable {
            mutableStateOf("")
        }
        var active by rememberSaveable {
            mutableStateOf(false)
        }

        SearchAppBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = {active = false},
            active = active,
            onActiveChange = { active = it } ,
            content = { /*TODO*/ },
            label = "Cari latihan",
            modifier = modifier,
        )

        LazyRow (
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(filter.size){
                FilterItem(filter[it])
            }
        }

        LazyColumn (
            modifier = modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(data.size){latihan ->
                CardLatihanItem(
                    latihan = data[latihan],
                    modifier = modifier,
                    onCLick = { onNavigateToLatihanForm(data[latihan].id) }
                )
            }
        }

    }
}

@Composable
fun FilterItem(filter: Filter) {
    var selected by rememberSaveable {
        mutableStateOf(false)
    }
    val selectedFilter = filter.id

    FilterChip(
        selected = selected,
        onClick = { selected = !selected },
        label = { Text(text = filter.name) },
        leadingIcon = {
            if (selected){
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        },
        modifier = Modifier
            .padding(end = 8.dp),
        colors = FilterChipDefaults.filterChipColors(
            labelColor = OnBlueSecondary,
            selectedLabelColor = Color.White,
            selectedLeadingIconColor = Color.White,
            selectedContainerColor = DarkBlue,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if(selected) DarkBlue else OnBlueSecondary
        )
    )
}