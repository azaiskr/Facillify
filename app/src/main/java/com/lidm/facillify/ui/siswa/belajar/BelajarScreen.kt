package com.lidm.facillify.ui.siswa.belajar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.R
import com.lidm.facillify.ui.components.SmallButton
import com.lidm.facillify.ui.theme.OnBlueSecondary
import com.lidm.facillify.util.installAPK
import com.lidm.facillify.util.isAppInstalled
import com.lidm.facillify.util.launchAPK

@Composable
fun BelajarScreen(
    modifier: Modifier,
    onBelajarClick: () -> Unit,
    onLatihanClick: () -> Unit,
) {
    val context = LocalContext.current
    val unityPackageName = "com.unity.template.ar_mobile"
    val apkName = "ar_faciliffy"

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
    ) {
        CardBelajarLandingpage(
            modifier = modifier,
            content = {
                ContentCardBelajar(
                    modifier = modifier,
                    navigateToMateriBelajar = onBelajarClick,
                )
            },
        )
        Spacer(modifier = modifier.height(16.dp))
        CardBelajarLandingpage(
            modifier = modifier,
            content = {
                ContentCardLatihan(
                    modifier = modifier,
                    navigateToLatihan = onLatihanClick,
                )
            },
        )
        Spacer(modifier = modifier.height(16.dp))
        CardBelajarLandingpage(
            modifier = modifier,
            content = {
                ContentCardAR(
                    modifier = modifier,
                    navigateToARApp = {
                        if (isAppInstalled(context, unityPackageName)) {
                            launchAPK(context, unityPackageName)
                        } else {
                            installAPK(context, apkName)
                        }
                    },
                )
            },
        )

    }
}

@Composable
fun CardBelajarLandingpage(
    content: @Composable () -> Unit,
    modifier: Modifier,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = OnBlueSecondary),
        modifier = modifier
            .fillMaxWidth()
    ) {
        content()
    }
}

@Composable
fun ContentCardBelajar(
    modifier: Modifier,
    navigateToMateriBelajar: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.belajar),
            contentDescription = null,
            modifier = modifier
                .weight(1f)
                .size(160.dp),
            contentScale = ContentScale.Crop,
        )
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.End,
            modifier = modifier
                .weight(1.5f)
        ) {
            Text(
                text = "Materi\nBelajar",
                fontSize = 32.sp,
                color = Color.White,
                lineHeight = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End
            )
            Spacer(modifier = modifier.height(8.dp))
            SmallButton(onClick = { navigateToMateriBelajar() }, labelText = "Lihat Materi")
        }
    }
}

@Composable
fun ContentCardLatihan(
    modifier: Modifier,
    navigateToLatihan: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start,
            modifier = modifier
                .weight(1.5f)
        ) {
            Text(
                text = "Latihan\nSoal",
                fontSize = 32.sp,
                lineHeight = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = modifier.height(8.dp))
            SmallButton(onClick = { navigateToLatihan() }, labelText = "Lihat Latihan")
        }
        Image(
            painter = painterResource(id = R.drawable.latihan),
            contentDescription = null,
            modifier = modifier
                .weight(1f)
                .size(160.dp),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun ContentCardAR(
    modifier: Modifier,
    navigateToARApp: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ar),
            contentDescription = null,
            modifier = modifier
                .weight(1f)
                .size(160.dp),
            contentScale = ContentScale.FillWidth,
        )
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.End,
            modifier = modifier
                .weight(1.5f)
        ) {
            Text(
                text = "Augmented\nReality",
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End
            )
            Spacer(modifier = modifier.height(8.dp))
            SmallButton(onClick = { navigateToARApp() }, labelText = "Buka Aplikasi")
        }
    }
}


@Composable
@Preview(showBackground = true)
fun SiswaBelajarScreenPreview(
    modifier: Modifier = Modifier,
) {
    BelajarScreen(modifier = modifier, onBelajarClick = {}, onLatihanClick = {})
}

@Preview
@Composable
fun CardPreview(
    modifier: Modifier = Modifier,
) {
    CardBelajarLandingpage(
        content = { ContentCardBelajar(modifier = modifier, navigateToMateriBelajar = {}) },
        modifier = modifier
    )
}

@Composable
@Preview
fun ContentCardARPreview(
    modifier: Modifier = Modifier,
) {
    ContentCardAR(modifier = modifier, navigateToARApp = {})
}