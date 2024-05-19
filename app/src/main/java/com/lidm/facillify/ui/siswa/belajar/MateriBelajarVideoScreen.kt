package com.lidm.facillify.ui.siswa.belajar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
fun MateriBelajarVideoScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        val title = "Video 1"
        val desc = "Pada video ini kita akan mempelajari mengenai bangun ruang yang ada di sekitar kita"

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

        ListMateriVideo(modifier, title, desc)

    }
}

@Composable
fun ListMateriVideo(
    modifier: Modifier,
    titleVide: String,
    descVideo: String,
) {
    var query by rememberSaveable {
        mutableStateOf("")
    }
    var active by rememberSaveable {
        mutableStateOf(false)
    }
    
    SearchAppBar(
        query = query,
        onQueryChange = { query = it } ,
        onSearch = {active = false},
        active = active,
        onActiveChange = {active = it} ,
        content = { /*TODO*/ },
        label = "Cari video" ,
        modifier = modifier
    )
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ){
        this.items(4){
            MateriVideoItem(
                modifier = modifier,
                onClick = { /*TODO*/ },
                titleVideo = titleVide,
                descVideo = descVideo
            )
        }
    }
}

@Composable
fun MateriVideoItem(
    modifier: Modifier,
    onClick: () -> Unit,
    titleVideo: String,
    descVideo: String,
) {
    Card (
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = SecondaryBlue,
            contentColor = DarkBlue,
        )
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            //TODO: ExoPlayer
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "video",
                modifier = Modifier
                    .size(width = 160.dp, height = 120.dp),
                contentScale = ContentScale.Crop
            )
            Column (
                verticalArrangement = Arrangement.Center,
                modifier = modifier.padding(16.dp)
            ) {
                Text(
                    text = titleVideo,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = descVideo,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                    maxLines = 3,
                    modifier = modifier.padding(top = 8.dp),
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

