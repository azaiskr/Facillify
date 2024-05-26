package com.lidm.facillify.ui.siswa

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lidm.facillify.R
import com.lidm.facillify.ui.components.CardLatihanItem
import com.lidm.facillify.ui.siswa.belajar.MateriBelajarItem
import com.lidm.facillify.ui.siswa.belajar.dummyDataLatihan
import com.lidm.facillify.ui.siswa.belajar.dummyDataMateri
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.Green
import com.lidm.facillify.ui.theme.Grey
import com.lidm.facillify.ui.theme.OnBlueSecondary
import com.lidm.facillify.ui.theme.SecondaryBlue

@Composable
fun SiswaHomeScreen(
    modifier: Modifier = Modifier,
    onItemBelajarClick: (Int) -> Unit,
    onItemLatihanClick: (Int) -> Unit,
    onNavigateToBelajar: () -> Unit,
    onNavigateToLatihan: () -> Unit,
    onNavigateToChatbot: () -> Unit,
) {
    //viewModel
    //state

    HomeScreenContent(
        modifier = modifier,
        onItemBelajarClick = onItemBelajarClick,
        onItemLatihanClick = onItemLatihanClick,
        onNavigateToBelajar = onNavigateToBelajar,
        onNavigateToLatihan = onNavigateToLatihan,
        onNavigateToChatbot = onNavigateToChatbot,
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    onItemBelajarClick: (Int) -> Unit = {},
    onItemLatihanClick: (Int) -> Unit = {},
    onNavigateToBelajar: () -> Unit = {},
    onNavigateToLatihan: () -> Unit = {},
    onNavigateToChatbot: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Button(
            onClick = { onNavigateToChatbot() },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
                .height(56.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Blue, Black
                        ),
                    ),
                    shape = RoundedCornerShape(16.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                Color.Transparent
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier.padding(vertical = 4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.chatbot),
                    contentDescription = null,
                    modifier = modifier.size(32.dp)
                )
                Text(
                    text = "Tanya AI",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }


        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Belajar",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Black,
                            color = Blue,
                            modifier = modifier.align(Alignment.CenterStart)
                        )
                        TextButton(
                            onClick = { onNavigateToBelajar() },
                            modifier = modifier.align(Alignment.CenterEnd)
                        ) {
                            Text(
                                text = "Selengkapnya",
                                style = MaterialTheme.typography.labelSmall,
                                color = Blue,
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                                contentDescription = null,
                                tint = Blue,
                                modifier = modifier
                                    .padding(start = 8.dp)
                                    .size(16.dp)
                            )
                        }
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(dummyDataMateri.size) {
                            MateriBelajarItem(
                                modifier = modifier,
                                onClick = { onItemBelajarClick(dummyDataMateri[it].id) },
                                materi = dummyDataMateri[it]
                            )
                        }
                    }
                }
            }
            item {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Latihan Soal",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Black,
                        color = Blue,
                        modifier = modifier.align(Alignment.CenterStart)
                    )
                    TextButton(
                        onClick = { onNavigateToLatihan() },
                        modifier = modifier.align(Alignment.CenterEnd)
                    ) {
                        Text(
                            text = "Selengkapnya",
                            style = MaterialTheme.typography.labelSmall,
                            color = Blue,
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                            contentDescription = null,
                            tint = Blue,
                            modifier = modifier
                                .padding(start = 8.dp)
                                .size(16.dp)
                        )
                    }
                }
            }
            items(dummyDataLatihan.size) {
                Box(modifier = modifier.padding(horizontal = 16.dp)) {
                    CardLatihanItem(
                        latihan = dummyDataLatihan[it],
                        modifier = modifier,
                        onCLick = { onItemLatihanClick(it) }
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreenContent(
        modifier = Modifier
    )
}
