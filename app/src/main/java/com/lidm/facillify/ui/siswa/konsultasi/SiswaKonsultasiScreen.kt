package com.lidm.facillify.ui.siswa.konsultasi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lidm.facillify.R
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.OnBlueSecondary
import com.lidm.facillify.ui.theme.SecondaryBlue


data class Konsultant(
    var nama: String,
    var profesi: String,
    var profilImg: Int,
)
@Preview
@Composable
fun SiswaKonsultasiScreen(
    modifier: Modifier = Modifier
) {

    val data = listOf(
        Konsultant(
            "Dr. Ir. H. Fathul Muzakki, M. Sc.",
            "Psikolog",
            R.drawable.ic_launcher_background
        ),
        Konsultant(
            "Dr. Ir. H. Fathul Muzakki, M. Sc.",
            "Psikolog",
            R.drawable.ic_launcher_background
        ),
        Konsultant(
            "Dr. Ir. H. Fathul Muzakki, M. Sc.",
            "Psikolog",
            R.drawable.ic_launcher_background
        )
    )

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

    ChatList(
        data = data,
        modifier = modifier
    )
}

@Composable
fun ChatList(
    data: List<Konsultant>,
    modifier: Modifier
) {
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(data.size){
            ChatItem(
                data = data[it],
                modifier = modifier,
                onClick = { /*TODO*/ }
            )
        }
    }
}

@Composable
fun ChatItem(
    data: Konsultant,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Card (
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = SecondaryBlue,
            contentColor = DarkBlue
        )
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
        ){
            Image(
                painter = painterResource(id = data.profilImg),
                contentDescription = null,
                modifier = modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column (
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = data.nama,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = data.profesi,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
        }
    }
}
