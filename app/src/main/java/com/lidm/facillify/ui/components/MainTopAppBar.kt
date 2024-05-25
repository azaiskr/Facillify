package com.lidm.facillify.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidm.facillify.R
import com.lidm.facillify.ui.theme.DarkBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    modifier: Modifier = Modifier,
    sectionTitle: String = "",
    backIcon: Boolean = false,
    onBackClick: () -> Unit,
    onProfileClick: () -> Unit,
    profileIcon: Boolean = true,
    isHide: Boolean = false,
) {
    if (!isHide) {
        TopAppBar(
            title = {
                Text(
                    text = sectionTitle,
                    color = DarkBlue,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                if (backIcon) {
                    IconButton(
                        onClick = { onBackClick() },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_arrow_back),
                            contentDescription = "back",
                            tint = DarkBlue
                        )
                    }
                }
            },
            actions = {
                if (profileIcon) {
                    ProfileIcon(
                        modifier = modifier,
                        onClick = onProfileClick
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                navigationIconContentColor = DarkBlue,
                titleContentColor = DarkBlue
            )
        )
    }
}

@Composable
fun ProfileIcon(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = null,
        modifier = modifier
            .padding(end = 8.dp)
            .clip(CircleShape)
            .size(32.dp)
            .clickable { onClick() },
        contentScale = ContentScale.Crop
    )
}
