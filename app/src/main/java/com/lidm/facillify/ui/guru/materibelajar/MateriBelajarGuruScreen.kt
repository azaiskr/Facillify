package com.lidm.facillify.ui.guru.materibelajar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.lidm.facillify.data.local.MateriBelajar
import com.lidm.facillify.data.local.listMateri
import com.lidm.facillify.data.remote.response.MaterialList
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.SearchAppBar
import com.lidm.facillify.ui.responseStateScreen.ErrorScreen
import com.lidm.facillify.ui.responseStateScreen.LoadingScreen
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.SecondaryBlue
import com.lidm.facillify.ui.viewmodel.MateriBelajarViewModel
import com.lidm.facillify.util.ResponseState

@Composable
fun MateriBelajarGuruScreen(
    onItemMateriBelajarClicked: (String) -> Unit
) {
    val viewModel: MateriBelajarViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
    val materiResponse by viewModel.materialResponse.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getMaterial()
    }

    when (materiResponse) {
        is ResponseState.Loading -> LoadingScreen()
        is ResponseState.Success -> {
            val materiBelajar = (materiResponse as ResponseState.Success).data
            Column(
            ) {
                var query by rememberSaveable { mutableStateOf("") }
                var active by rememberSaveable { mutableStateOf(false) }

                SearchAppBar(
                    query = query,
                    onQueryChange = { query = it },
                    onSearch = { active = false },
                    active = active,
                    onActiveChange = { active = false },
                    label = "Cari materi",
                    modifier = Modifier
                )

                val filteredMateri = if (query != "") {
                    materiBelajar.filter {
                        it.title.contains(query, ignoreCase = true)
                    }
                } else {
                    materiBelajar
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    items(filteredMateri.size) {
                        MateriBelajarGuruItem(
                            materi = filteredMateri[it],
                            onItemClicked = onItemMateriBelajarClicked
                        )
                    }
                }
            }
        }
        is ResponseState.Error -> {
            ErrorScreen(
                onTryAgain = {viewModel.getMaterial()}
            )
        }
    }

}

@Composable
fun MateriBelajarGuruItem(
    materi: MateriBelajar,
    onItemClicked: (String) -> Unit,
) {
    Card(
        modifier = Modifier.clickable { onItemClicked(materi.id) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = SecondaryBlue,
            contentColor = DarkBlue,
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = materi.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = materi.title,
                modifier = Modifier.padding(vertical = 8.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = materi.desc,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 4,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MateriBelajarGuruScreenPreview() {
    MateriBelajarGuruScreen(
        onItemMateriBelajarClicked = {}
    )
}