package com.lidm.facillify.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.R
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.SecondaryBlue

@Composable
fun KonsultasiCard(
    image: Painter,
    name: String,
    guruMatkul: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = SecondaryBlue
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(modifier = Modifier.size(64.dp), painter = image, contentDescription = "photo profile")

            Spacer(modifier = Modifier.size(16.dp))
            Column {
                Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = DarkBlue)
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = guruMatkul, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Black)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun KonsultasiCardPreview() {
    KonsultasiCard(
        image = painterResource(id = R.drawable.pp_deafult),
        name = "Nama Guru",
        guruMatkul = "Guru Matematika"
    )
}

