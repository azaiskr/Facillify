package com.lidm.facillify.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.R
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.SecondaryBlue


@Composable
fun StudentCard(
    linkImage: String,
    bearerToken: String,
    nameStudent: String,
    numberStudent: Long,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = SecondaryBlue
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Image
            DynamicSizeImage(
                imageUrl = linkImage,
                modifier = Modifier.size(96.dp),
                bearerToken = bearerToken
            )

            Spacer(modifier = Modifier.width(16.dp))
            //Details
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                //Name
                Text(
                    text = "Nama Siswa",
                    fontSize = 12.sp,
                    color = DarkBlue,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = nameStudent,
                    fontSize = 14.sp,
                    color = Black,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
                //NISN
                Text(
                    text = "NISN",
                    fontSize = 12.sp,
                    color = DarkBlue,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = numberStudent.toString(),
                    fontSize = 14.sp,
                    color = Black,
                    maxLines = 1
                )

                //Button
                Spacer(modifier = Modifier.height(16.dp))
                MiniButton(
                    text = "Cek Selengkapnya",
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
fun MiniButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        content = {
            Text(
                text = text,
                color = Color.White,
                fontSize = 8.sp,
            )
        }
    )
}