package com.lidm.facillify.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidm.facillify.R
import com.lidm.facillify.data.remote.response.ProfileResponse
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.viewmodel.ProfileViewModel
import com.lidm.facillify.util.ResponseState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    modifier: Modifier = Modifier,
    sectionTitle: String = "",
    contentColor: Color = DarkBlue,
    backIcon: Boolean = false,
    onBackClick: () -> Unit,
    onProfileClick: () -> Unit,
    profileIcon: Boolean = true,
    isHide: Boolean = false,
    isHome: Boolean = false
) {
    val context: Context = LocalContext.current
    val profileViewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context.applicationContext)
    )
    val preferences by profileViewModel.getSession().observeAsState()
    val profileResponse = profileViewModel.profileResponse.collectAsState()

    LaunchedEffect(preferences) {
        preferences?.let {
            profileViewModel.getUserProfile(it.email)
        }
    }

    when (profileResponse.value) {
        is ResponseState.Loading -> {

        }
        is ResponseState.Error -> {
            if (!isHide) {
                TopAppBar(
                    title = {
                        if (isHome) {
                            Text(
                                text = "Hallo, _",
                                color = contentColor,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Text(
                                text = sectionTitle,
                                color = contentColor,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    },
                    navigationIcon = {
                        if (backIcon) {
                            IconButton(
                                onClick = { onBackClick() },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.round_arrow_back),
                                    contentDescription = "back",
                                    tint = contentColor
                                )
                            }
                        }
                    },
                    actions = {
                        if (profileIcon) {
                            ProfileIcon(
                                modifier = modifier,
                                onClick = onProfileClick,
                                imageUrl = "",
                                bearerToken = preferences?.token.toString()
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
        is ResponseState.Success -> {
            val profileData = (profileResponse.value as ResponseState.Success<ProfileResponse>).data

            if (!isHide) {
                TopAppBar(
                    title = {
                        if (isHome) {
                            Text(
                                text = "Hallo, " + profileData.result.name,
                                color = contentColor,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Text(
                                text = sectionTitle,
                                color = contentColor,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    },
                    navigationIcon = {
                        if (backIcon) {
                            IconButton(
                                onClick = { onBackClick() },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.round_arrow_back),
                                    contentDescription = "back",
                                    tint = contentColor
                                )
                            }
                        }
                    },
                    actions = {
                        if (profileIcon) {
                            ProfileIcon(
                                modifier = modifier,
                                onClick = onProfileClick,
                                imageUrl = profileData.result.profile_image_url.toString(),
                                bearerToken = preferences?.token.toString()
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
    }
}

@Composable
fun ProfileIcon(
    modifier: Modifier,
    imageUrl: String,
    bearerToken: String,
    onClick: () -> Unit,
) {
    /*Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = null,
        modifier = modifier
            .padding(end = 8.dp)
            .clip(CircleShape)
            .size(32.dp)
            .clickable { onClick() },
        contentScale = ContentScale.Crop
    )*/
    Column(
        modifier = modifier.clickable { onClick() },
    ) {
        DynamicSizeImage(imageUrl = imageUrl, bearerToken = bearerToken, modifier = modifier.size(32.dp).padding(end = 8.dp))
    }

}
