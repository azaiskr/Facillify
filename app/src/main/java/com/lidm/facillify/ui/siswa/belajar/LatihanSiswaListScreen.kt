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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidm.facillify.data.local.Filter
import com.lidm.facillify.data.local.dummyDataFilter
import com.lidm.facillify.data.remote.response.QuizListItem
import com.lidm.facillify.data.remote.response.QuizListResponse
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.CardLatihanItem
import com.lidm.facillify.ui.components.DialogConfirm
import com.lidm.facillify.ui.components.SearchAppBar
import com.lidm.facillify.ui.responseStateScreen.ErrorScreen
import com.lidm.facillify.ui.responseStateScreen.LoadingScreen
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.OnBlueSecondary
import com.lidm.facillify.ui.viewmodel.LatihanSiswaViewModel
import com.lidm.facillify.util.ResponseState

@Composable
fun LatihanSiswaListScreen(
    modifier: Modifier,
    onNavigateToLatihanForm: (String) -> Unit,
) {

    val viewModel: LatihanSiswaViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
    val quizListResponse by viewModel.quizList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getQuizList()
    }
    when (quizListResponse) {
        is ResponseState.Loading -> {
            LoadingScreen()
        }

        is ResponseState.Success -> {
            val response = (quizListResponse as ResponseState.Success<QuizListResponse>).data
            val quizList = response.result
            ListLatihan(
                data = quizList,
                filter = dummyDataFilter,
                modifier = modifier,
                onNavigateToLatihanForm = onNavigateToLatihanForm
            )
        }

        is ResponseState.Error -> {
            ErrorScreen(
                onTryAgain = { viewModel.getQuizList() }
            )
        }
    }

}

@Composable
fun ListLatihan(
    data: List<QuizListItem>,
    filter: List<Filter>,
    modifier: Modifier,
    onNavigateToLatihanForm: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        var query by rememberSaveable {
            mutableStateOf("")
        }
        var active by rememberSaveable {
            mutableStateOf(false)
        }

        var showDialog by rememberSaveable {
            mutableStateOf(false)
        }

        var selectedLatihanId by rememberSaveable {
            mutableStateOf<String?>(null)
        }

        SearchAppBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = { active = false },
            active = false,
            onActiveChange = { active = false },
            label = "Cari latihan",
            modifier = modifier,
        )

        var selectedFilters by rememberSaveable { mutableStateOf<Set<Filter>>(emptySet()) }

        val filteredData = if (selectedFilters.isNotEmpty() || query != "") {
            data.filter { latihan ->
                latihan.title.contains(query, ignoreCase = true)
            }
        } else {
            data
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(filter.size) {
                FilterItem(
                    filter = filter[it],
                    isSelected = selectedFilters.contains(filter[it]),
                    onFilterSelected = { filter ->
                        selectedFilters = if (selectedFilters.contains(filter)) {
                            selectedFilters - filter
                        } else {
                            selectedFilters + filter
                        }
                    }
                )
            }
        }

        LazyColumn(
            modifier = modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredData.size) { index ->
                    CardLatihanItem(
                        latihan = filteredData[index],
                        modifier = modifier,
                        onCLick = {
                            selectedLatihanId = filteredData[index].id
                            showDialog = true
                        }
                    )
                }
        }

        if (showDialog && selectedLatihanId != null) {
            DialogConfirm(
                onDismiss = { showDialog = false },
                onConfirm = {
                    showDialog = false
                    onNavigateToLatihanForm(selectedLatihanId!!)
                },
                title = "Kerjakan Latihan?",
                msg = "Yakin ingin mengerjakan latihan ini? Kamu tidak akan dapat kembali ketika latihan dimulai. Pastikan kamu sudah siap ya!",
                confirmLabel = "Kerjakan",
                dismissLabel = "Kembali"
            )
        }
    }
}

@Composable
fun FilterItem(
    filter: Filter,
    isSelected: Boolean,
    onFilterSelected: (Filter) -> Unit,
) {
    FilterChip(
        selected = isSelected,
        onClick = { onFilterSelected(filter) },
        label = { Text(text = filter.name) },
        leadingIcon = {
            if (isSelected) {
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
            color = if (isSelected) DarkBlue else OnBlueSecondary
        )
    )
}