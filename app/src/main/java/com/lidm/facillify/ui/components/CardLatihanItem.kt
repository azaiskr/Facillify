package com.lidm.facillify.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.R
import com.lidm.facillify.ui.siswa.belajar.LatihanItem
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.OnBlueSecondary
import com.lidm.facillify.ui.theme.Yellow

@Composable
fun CardLatihanItem(
    latihan: LatihanItem,
    modifier: Modifier,
    onCLick: () -> Unit,
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCLick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.Transparent,
            contentColor = DarkBlue,
        ),
        border = BorderStroke(
            width = 2.dp,
            color = OnBlueSecondary,
        )
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth(),
            ) {
                Image(
                    painter = if (latihan.done) painterResource(id = R.drawable.donebadge) else painterResource(id = R.drawable.undonedadge) ,
                    contentDescription = null,
                    modifier = modifier
                        .size(40.dp)
                )
                Text(
                    text = "${latihan.jmlSoal}\nSoal",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Yellow,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.Black,
                    modifier = modifier
                        .offset(45.dp, (6).dp)
                )
                Text(
                    text = "${latihan.waktu} Menit",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnBlueSecondary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier
                        .align(Alignment.TopEnd)
                )
            }
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = latihan.judul,
                fontSize = 20.sp,
                color = DarkBlue,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = latihan.deskripsi,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 16.sp,
                maxLines = 2,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Preview
@Composable
fun CardLatihanItemPreview() {
    CardLatihanItem(
        latihan = LatihanItem(
            id = 1,
            jmlSoal = 10,
            waktu = 20,
            judul = "Latihan 1",
            deskripsi = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ",
            subBab = "Bangun Ruang",
            done = true,
            questions = emptyList()
        ),
        modifier = Modifier,
        onCLick = {}
    )
}