package com.lidm.facillify.navigation.utils

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem (
    val title: String,
    val icon: ImageVector,
    val iconSelected: ImageVector? = null,
    val screen: NavigationScreen,
) {
}