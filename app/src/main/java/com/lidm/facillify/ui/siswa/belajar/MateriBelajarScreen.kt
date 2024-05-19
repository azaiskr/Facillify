package com.lidm.facillify.ui.siswa.belajar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.R
import com.lidm.facillify.ui.components.SearchAppBar
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.SecondaryBlue

data class MateriBelajar(
    val id: Int,
    val image: Int,
    val title: String,
    val desc: String,
)

val dummyDataMateri = listOf(
    MateriBelajar(
        id = 1,
        image = R.drawable.ic_launcher_background,
        title = "Bangun Ruang",
        desc = "Pelajari berbagai macam bentuk bangun ruang yang ada di sekitarmu"),

    MateriBelajar(
        id = 2,
        image = R.drawable.ic_launcher_background,
        title = "SPLDV",
        desc = "Sistem Persamaan Linear Dua Variabel adalah sistem persamaan yang mempunyai dua variabel yang berbentuk persamaan linear."),
    MateriBelajar(
        id = 3,
        image = R.drawable.ic_launcher_background,
        title = "Trigonometri",
        desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec euismod, nisl eget consectetur pellentesque,"),
)

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
            materi = dummyDataMateri,
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
        active = active,
        onActiveChange = {active = it} ,
        content = { /*TODO*/ },
        label = "Cari materi",
        modifier = modifier
    )
    LazyVerticalGrid(
        columns = GridCells.Adaptive(147.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        this.items(materi.size){item ->
            MateriBelajarItem(
                modifier = modifier,
                onClick = { onItemClick(materi[item].id) },
                materi = materi[item]
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
            Image(
                painter = painterResource(id = materi.image),
                contentDescription = "Materi",
                modifier = modifier
                    .height(100.dp),
                contentScale = ContentScale.FillHeight,
                alignment = Alignment.Center
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
                overflow = TextOverflow.Clip,
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
            image = R.drawable.ic_launcher_background,
            title = "Bangun Ruang",
            desc = "Pelajari berbagai macam bentuk bangun ruang yang ada di sekitarmu")
    )
}
