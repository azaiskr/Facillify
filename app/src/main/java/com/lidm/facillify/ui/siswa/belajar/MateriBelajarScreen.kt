package com.lidm.facillify.ui.siswa.belajar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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

@Preview
@Composable
fun MateriBelajarScreen(
    modifier: Modifier = Modifier,

){
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        var active by rememberSaveable {
            mutableStateOf(false)
        }

        SearchAppBar(
            query = "",
            onQueryChange = {},
            onSearch = {active = false},
            active = false,
            onActiveChange = {active = false} ,
            content = { /*TODO*/ },
            label = "Cari materi",
            modifier = modifier
        )
        Spacer(modifier = modifier.height(16.dp))

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
        MateriBelajarGrid()
    }
}

@Composable
fun MateriBelajarGrid(
    modifier: Modifier = Modifier,
//    materi : List<>
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(147.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        this.items(3){item ->
            MateriBelajarItem(
                modifier = modifier,
                onClick = {/*TODO*/}
            )
        }
    }
}

@Composable
fun MateriBelajarItem(
    modifier: Modifier,
    onClick: () -> Unit
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
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Materi",
                modifier = modifier
                    .height(100.dp),
                contentScale = ContentScale.FillHeight,
                alignment = Alignment.Center
            )
            Text(
                modifier = modifier.padding(top = 16.dp, bottom = 8.dp),
                text = "Bangun Ruang",
                fontSize = 16.sp,
                color = DarkBlue,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Pelajari berbagai macam bentuk bangun ruang yang ada di sekitarmu",
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
        onClick = {}
    )
}
