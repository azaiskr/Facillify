package com.lidm.facillify.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lidm.facillify.R
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.OnBlueSecondary
import com.lidm.facillify.ui.theme.SecondaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    content: @Composable () -> Unit,
    label: String,
    modifier: Modifier,
) {
    DockedSearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange,
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.round_search), contentDescription = null)
        },
        placeholder = { Text(label) },
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = SearchBarDefaults.colors(
            containerColor = SecondaryBlue,
            dividerColor = MaterialTheme.colorScheme.background,
            inputFieldColors = TextFieldDefaults.colors(
                focusedTextColor = DarkBlue,
                unfocusedTextColor = OnBlueSecondary,
                unfocusedPlaceholderColor = OnBlueSecondary,
                focusedPlaceholderColor = OnBlueSecondary,
                cursorColor = OnBlueSecondary,
                focusedLeadingIconColor = DarkBlue,
                unfocusedLeadingIconColor = OnBlueSecondary,
            )
        )
    ) {
        content()
    }
}