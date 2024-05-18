package com.lidm.facillify.navigation.utils

import androidx.compose.ui.graphics.painter.Painter

data class NavigationItem (
    val title: String,
    val icon: Painter,
    val iconSelected: Painter? = null,
    val screen: Screen,
)