package com.lidm.facillify.ui.siswa.belajar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lidm.facillify.R
import com.lidm.facillify.data.local.MateriBelajar
import com.lidm.facillify.data.local.listMateri
import com.lidm.facillify.data.local.paketMateri.materi_bangun_ruang
import com.lidm.facillify.ui.components.SearchAppBar
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.SecondaryBlue

@Composable
fun MateriBelajarScreen(
    modifier: Modifier,
    onNavigateToMateriBelajarDetail: (Int) -> Unit,
){
    Column (
        modifier = modifier
            .fillMaxSize()
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

        MateriBelajarGrid(
            modifier = modifier,
            materi = listMateri,
            onItemClick = onNavigateToMateriBelajarDetail
        )
    }
}

@Composable
fun MateriBelajarGrid(
    modifier: Modifier = Modifier,
    materi : List<MateriBelajar>,
    onItemClick: (Int) -> Unit
) {
    var query by rememberSaveable {
        mutableStateOf("")
    }

    var active by rememberSaveable {
        mutableStateOf(false)
    }


    SearchAppBar(
        query = query,
        onQueryChange = {query = it},
        onSearch = {active = false},
        active = false,
        onActiveChange = {active = false} ,
        label = "Cari materi",
        modifier = modifier
    )

    val filteredMateri =if (query != ""){
        materi.filter {
            it.title.contains(query, ignoreCase = true)
        }
    } else {
        materi
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(147.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        this.items(filteredMateri.size){index ->
            MateriBelajarItem(
                modifier = modifier,
                onClick = { onItemClick(filteredMateri[index].id) },
                materi = filteredMateri[index]
            )
        }
    }
}

@Composable
fun MateriBelajarItem(
    modifier: Modifier,
    onClick: () -> Unit,
    materi: MateriBelajar,
) {
    Card (
        modifier = modifier
            .width(147.dp)
            .clickable { onClick() }
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = SecondaryBlue
        )

    ){
        Column (
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(materi.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.connectivity5),
                contentDescription = "Materi Poster",
                contentScale = ContentScale.FillHeight,
                modifier = modifier
                    .clip(RoundedCornerShape(8.dp))
                    .height(100.dp),
            )
            Text(
                modifier = modifier.padding(top = 16.dp, bottom = 8.dp),
                text = materi.title,
                fontSize = 16.sp,
                color = DarkBlue,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = materi.desc,
                color = DarkBlue,
                fontSize = 12.sp,
                lineHeight = 12.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun MateriBelajarItemPreview() {
    MateriBelajarItem(
        modifier = Modifier,
        onClick = {},
        materi = MateriBelajar(
            id = 1,
            image = "",
            title = "Bangun Ruang",
            desc = "Pelajari berbagai macam bentuk bangun ruang yang ada di sekitarmu",
            materiVideo = listOf()
        ),
    )
}
