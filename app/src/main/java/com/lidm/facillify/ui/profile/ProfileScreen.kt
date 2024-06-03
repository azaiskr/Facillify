package com.lidm.facillify.ui.profile

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.lidm.facillify.R
import com.lidm.facillify.data.remote.response.ProfileResponse
import com.lidm.facillify.data.remote.response.UserProfile
import com.lidm.facillify.di.Inject
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.DialogConfirm
import com.lidm.facillify.ui.components.DynamicSizeImage
import com.lidm.facillify.ui.components.MainButton
import com.lidm.facillify.ui.components.SecondaryButton
import com.lidm.facillify.ui.components.ShowToast
import com.lidm.facillify.ui.responseStateScreen.ErrorScreen
import com.lidm.facillify.ui.responseStateScreen.LoadingScreen

import com.lidm.facillify.ui.theme.AlertRed
import com.lidm.facillify.ui.theme.DarkBlue
import com.lidm.facillify.ui.theme.OnBlueSecondary
import com.lidm.facillify.ui.theme.SecondaryBlue
import com.lidm.facillify.ui.viewmodel.ProfileViewModel
import com.lidm.facillify.util.ResponseState
import com.lidm.facillify.util.Role

@Composable
fun ProfileScreen(
    modifier: Modifier,
    navigateToFormTambahDataOrtu: () -> Unit = {},
    context: Context = LocalContext.current,
) {
    val profileViewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context.applicationContext)
    )
    val profileResponse = profileViewModel.profileResponse.collectAsState()
    val uploadImageState = profileViewModel.uploadImageState.collectAsState()
    val preferences by profileViewModel.getSession().observeAsState()
    var showDialog by remember { mutableStateOf(false) }

    val swipeRefreshState = remember { SwipeRefreshState(isRefreshing = false) }

    LaunchedEffect(preferences) {
        preferences?.let {
            profileViewModel.getUserProfile(it.email)
        }
    }

    LaunchedEffect(uploadImageState.value) {
        when (uploadImageState.value) {
            is ResponseState.Success -> {
                Toast.makeText(context, "Foto profil berhasil diunggah", Toast.LENGTH_SHORT).show()
                profileViewModel.getUserProfile(preferences?.email.toString())
            }
            is ResponseState.Error -> {
                Toast.makeText(context, "Gagal mengunggah foto profil", Toast.LENGTH_SHORT).show()
            }
            else -> {
                // Do nothing for Loading state
            }
        }
        profileViewModel.getUserProfile(preferences?.email.toString())
    }

    SwipeRefresh(state = swipeRefreshState, onRefresh = { preferences?.let {
        profileViewModel.getUserProfile(
            it.email
        )
    } }){
        Column {
            when (profileResponse.value) {
                is ResponseState.Loading -> {
                    LoadingScreen()
                }
                is ResponseState.Success -> {
                    val profileData = (profileResponse.value as ResponseState.Success<ProfileResponse>).data
                    ProfileContent(
                        profileData = profileData.result,
                        modifier = modifier,
                        actionLogOut = { showDialog = true},
                        onClick = navigateToFormTambahDataOrtu,
                        onImageSelected = { uri ->
                            preferences?.email?.let { email ->
                                profileViewModel.uploadNewProfilePhoto(uri, email)
                            }
                        },
                        bearerToken = preferences?.token.toString()
                    )
                }
                is ResponseState.Error -> {
                    (profileResponse.value as ResponseState.Error).error?.let { ShowToast(message = it) }
                    ErrorScreen(
                        onTryAgain = {
                            preferences?.email?.let { profileViewModel.getUserProfile(it) }
                        }
                    )
                    MainButton(
                        modifier = Modifier.padding(48.dp),
                        onClick = { profileViewModel.logOut() },
                        labelText = "Keluar"
                    )
                }
            }
            Spacer(modifier = modifier.height(24.dp))
        }

        if (showDialog) {
            DialogConfirm(
                title = "Keluar Aplikasi?",
                msg = "Apakah anda yakin ingin keluar?",
                onConfirm = {
                    profileViewModel.logOut()
                    showDialog = false
                },
                onDismiss = {
                    showDialog = false
                },
                confirmLabel = "Ya",
                dismissLabel = "Tidak",
            )
        }
    }

}

@Composable
fun ProfileContent(
    profileData: UserProfile,
    modifier: Modifier,
    actionLogOut: () -> Unit = {},
    onClick: () -> Unit = {},
    onImageSelected: (Uri) -> Unit = {},
    bearerToken: String
) {
    val dataLabel = mutableListOf<String>()
    val dataValues = mutableListOf<String?>()

    dataLabel.add("Email")
    dataValues.add(profileData.email)

    dataLabel.add("Nama")
    dataValues.add(profileData.name)

    dataLabel.add("Tempat Lahir")
    dataValues.add(profileData.pob)

    dataLabel.add("Tanggal Lahir")
    dataValues.add(profileData.dob)

    dataLabel.add("Jenis Kelamin")
    dataValues.add(profileData.gender)

    dataLabel.add("Alamat")
    dataValues.add(profileData.address)

    dataLabel.add("No. HP")
    dataValues.add(profileData.phone_number)

    dataLabel.add("Agama")
    dataValues.add(profileData.religion)


    if (profileData.nisn != null) {
        dataLabel.add("NISN")
        dataValues.add(profileData.nisn)
    }

    if (profileData.type == "murid") {
        dataLabel.add("Email Wali")
        dataValues.add(profileData.parent_email)
    }

    if (profileData.job != null) {
        dataLabel.add("Pekerjaan")
        dataValues.add(profileData.job)
    }

    if (profileData.nip != null) {
        dataLabel.add("NIP")
        dataValues.add(profileData.nip)
    }

    // State to hold the selected profile image URI
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    // Launcher to pick an image from the gallery
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                profileImageUri = uri
                onImageSelected(uri)
            }
        }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = modifier
                .size(96.dp)
        ) {
            profileImageUri?.let { uri ->
                val bitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source)
                }
                /*Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop,
                )*/
                DynamicSizeImage(
                    modifier = modifier.size(96.dp)
                    , imageUrl = profileData.profile_image_url.toString()?:"",
                    bearerToken = bearerToken )

            } ?: run {
                /*Image(
                    painter = rememberImagePainter(data = profileData.profile_image_url),
                    contentDescription = null,
                    modifier = modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Fit,
                )*/
                DynamicSizeImage(
                    modifier = modifier.size(96.dp)
                    , imageUrl = profileData.profile_image_url.toString()?:"",
                    bearerToken = bearerToken)
                Log.d("ProfileContent", "ProfileContent: ${profileData.profile_image_url}")
            }
            IconButton(
                onClick = { launcher.launch("image/*") },
                modifier = modifier
                    .size(32.dp)
                    .background(Color.White, CircleShape)
                    .padding(4.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = SecondaryBlue
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile Picture",
                    tint = Color.Black,
                    modifier = modifier.padding(4.dp)
                )
            }
        }
        Text(
            text = profileData.name,
            modifier = modifier
                .padding(vertical = 16.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = modifier.height(24.dp))
        dataLabel.mapIndexed { index, label ->
            DetailProfileData(
                label = label,
                data = dataValues.getOrNull(index),
                modifier = modifier
            )
        }
        if (profileData.type == "murid" && profileData.parent_email == null) {
            Spacer(modifier = modifier.height(24.dp))
            SecondaryButton(
                modifier = modifier,
                onClick = { onClick() },
                outline = true,
                label = "Edit Email Orang Tua"
            )
        }
        Button(
            modifier = modifier
                .fillMaxWidth()
                .height(40.dp),
            onClick = { actionLogOut() },
            colors = ButtonDefaults.buttonColors(
                containerColor = AlertRed,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Keluar",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun DetailProfileData(
    label: String = "",
    data: String? = null,
    modifier: Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = OnBlueSecondary,
            modifier = modifier
                .weight(0.25f)
        )
        Text(
            text = data ?: "Tidak ada data",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = DarkBlue,
            textAlign = TextAlign.End,
            modifier = modifier
                .weight(0.75f)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview() {
    ProfileScreen(modifier = Modifier)
}
