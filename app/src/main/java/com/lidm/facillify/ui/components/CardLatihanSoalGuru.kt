package com.lidm.facillify.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.R
import com.lidm.facillify.ui.theme.AlertRed
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.SecondaryBlue

@Composable
fun CardLatihanSoalGuru(
    judulSoal: String,
    deskripsiSoal: String,
    waktuSoal: Int,
    deleteItem: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = SecondaryBlue
        )

    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ){
                Image(
                    //gak ketemu gambarnya
                    painter = painterResource(id = R.drawable.onboarding_image_one),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp),
                )
                Column {
                    IconButton(onClick = { deleteItem()}) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "delete item", tint = AlertRed)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$waktuSoal menit",
                        fontSize = 12.sp,
                        color = Blue,
                        fontWeight = FontWeight.SemiBold
                    )
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = judulSoal,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Blue
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = deskripsiSoal,
                fontSize = 12.sp,
                color = Blue,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CardLatihanSoalGuruPreview() {
    CardLatihanSoalGuru(
        judulSoal = "Soal 1",
        deskripsiSoal = "Deskripsi Soal 1",
        waktuSoal = 60,
        deleteItem = {}
    )
}