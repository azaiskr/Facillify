package com.lidm.facillify.ui.responseStateScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.lidm.facillify.ui.components.MainButton
import com.lidm.facillify.ui.theme.DarkBlue

@Composable
fun ErrorScreen(
    onTryAgain: () -> Unit
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Error Icon
        Image(
            painter = painterResource(id = R.drawable.connectivity5), // Replace with your error icon resource
            contentDescription = null,
            modifier = Modifier.size(240.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Error Message
        Text(
            text = "Oops! Ada masalah.",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBlue
        )
        Text(
            text = "Harap coba lagi nanti.",
            fontSize = 16.sp,
            color = DarkBlue
        )

        Spacer(modifier = Modifier.height(24.dp))
        MainButton(
            modifier = Modifier.padding(horizontal = 48.dp),
            onClick = onTryAgain,
            labelText = "Coba lagi"
        )

    }
}