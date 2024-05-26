package com.lidm.facillify.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.lidm.facillify.ui.theme.Black
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.theme.OnBlueSecondary
import com.lidm.facillify.ui.theme.SecondaryBlue

@Composable
fun MainFab(
    onClick: () -> Unit,
    isShown: Boolean,
) {
    if (isShown) {
        FloatingActionButton(
            onClick = onClick,
            containerColor = Blue,
            contentColor = SecondaryBlue,
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }

}