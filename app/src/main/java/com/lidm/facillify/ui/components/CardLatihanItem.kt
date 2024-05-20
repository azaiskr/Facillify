package com.lidm.facillify.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth(),
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
                    painter = painterResource(id = R.drawable.editor_choice),
                    contentDescription = null,
                    modifier = modifier
                        .size(40.dp),
                )
                Text(
                    text = "${latihan.jmlSoal}\nSoal",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Yellow,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = modifier
                        .offset(45.dp, (6).dp)
                )
                Text(
                    text = "${latihan.waktu} Menit",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = OnBlueSecondary,
                        fontWeight = FontWeight.SemiBold,
                    ),
                    modifier = modifier
                        .align(Alignment.TopEnd)
                )
            }
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = latihan.judul,
                style = TextStyle(
                    fontSize = 20.sp,
                    color = DarkBlue,
                    fontWeight = FontWeight.Bold,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = latihan.deskripsi,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                ),
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
            jmlSoal = 10,
            waktu = 20,
            judul = "Latihan 1",
            deskripsi = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ",
            subBab = "Bangun Ruang",
            done = true
        ),
        modifier = Modifier,
    )
}