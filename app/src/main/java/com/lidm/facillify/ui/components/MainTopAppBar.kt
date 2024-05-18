package com.lidm.facillify.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.lidm.facillify.R
import com.lidm.facillify.ui.theme.DarkBlue

@Composable
fun MainTopAppBar(
    modifier: Modifier = Modifier,
    sectionTitle: String = "",
    backIcon : Boolean = false,
    onBackClick: () -> Unit,
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = if (backIcon) modifier.padding(vertical = 14.dp) else modifier.padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
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
        Text(
            text = sectionTitle,
            color = DarkBlue,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}