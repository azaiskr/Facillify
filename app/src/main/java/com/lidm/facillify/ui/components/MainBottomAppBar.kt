package com.lidm.facillify.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon as NavIcon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lidm.facillify.R
import com.lidm.facillify.navigation.utils.NavigationItem
import com.lidm.facillify.navigation.utils.Screen
import com.lidm.facillify.ui.theme.DarkBlue

@Composable
fun MainBottomAppBar(
    navController: NavHostController,
    screen: List<Screen>,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar(
        containerColor = DarkBlue,
        modifier = modifier.height(64.dp)
    ) {
        val navigationItems = listOf(
            NavigationItem (
                title = "Home",
                icon = painterResource(id = R.drawable.location_away_rounded),
                iconSelected = painterResource(id = R.drawable.location_away_fill),
                screen = screen[0]
            ),
            NavigationItem (
                title = "Belajar",
                icon = painterResource(id = R.drawable.book_4_rounded),
                iconSelected = painterResource(id = R.drawable.book_4_fill),
                screen = screen[1]
            ),
            NavigationItem(
                title = "Konsultasi",
                icon = painterResource(id = R.drawable.forum_rounded),
                iconSelected = painterResource(id = R.drawable.forum_fill),
                screen = screen[2]
            ),
            NavigationItem (
                title = "Riwayat",
                icon = painterResource(id = R.drawable.analytics_rounded),
                iconSelected = painterResource(id = R.drawable.analytics_fill),
                screen = screen[3]
            )
        )

        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    if (currentRoute == item.screen.route && item.iconSelected != null ){
                        NavIcon(
                            painter = item.iconSelected,
                            contentDescription = item.title
                        )
                    } else {
                        NavIcon(
                            painter = item.icon,
                            contentDescription = item.title
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White,
                    indicatorColor = DarkBlue,
                ),
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}