package com.lidm.facillify.ui.guru.materibelajar

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.lidm.facillify.R
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.InputTextFieldDefault
import com.lidm.facillify.ui.theme.AlertRed
import com.lidm.facillify.ui.theme.Blue
import com.lidm.facillify.ui.viewmodel.TambahMateriBelajarViewModel
import com.lidm.facillify.util.ResponseState
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

@Composable
fun TambahMateriBelajarScreen(
    context: Context = LocalContext.current,
    onBackClick: () -> Unit
) {
    // ViewModel
    val tambahMateriBelajarViewModel: TambahMateriBelajarViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context.applicationContext)
    )

    // State dari ViewModel
    val createdMaterialState by tambahMateriBelajarViewModel.createdMaterial.collectAsState()
    val uploadVideoState by tambahMateriBelajarViewModel.uploadedVideoUrls.collectAsState()
    val uploadAudioState by tambahMateriBelajarViewModel.uploadedAudioUrls.collectAsState()

    // UI State
    var judulMateri by remember { mutableStateOf("") }
    var deskripsiMateri by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var videoLinks by remember { mutableStateOf(listOf("")) }
    var videoTitles by remember { mutableStateOf(listOf("")) }
    var videoDescriptions by remember { mutableStateOf(listOf("")) }
    var audioLinks by remember { mutableStateOf(listOf("")) }
    var audioNames by remember { mutableStateOf(listOf("")) }
    var showSnackbar by remember { mutableStateOf(false) }

    // Launchers for selecting media
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
        }
    }

    //LAUNCHED EFFECT
    LaunchedEffect(Unit) {
        tambahMateriBelajarViewModel.clearUploadedVideoUrls()
        tambahMateriBelajarViewModel.clearUploadedAudioUrls()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            ImageSection(imageUri, imageLauncher)
            TitleDescriptionSection(
                judulMateri = judulMateri,
                deskripsiMateri = deskripsiMateri,
                onJudulChange = { judulMateri = it },
                onDeskripsiChange = { deskripsiMateri = it }
            )
            VideoSection(
                videoLinks = videoLinks,
                videoTitles = videoTitles,
                videoDescriptions = videoDescriptions,
                onLinkChange = { index, link -> videoLinks = videoLinks.toMutableList().apply { this[index] = link } },
                onTitleChange = { index, title -> videoTitles = videoTitles.toMutableList().apply { this[index] = title } },
                onDescriptionChange = { index, desc -> videoDescriptions = videoDescriptions.toMutableList().apply { this[index] = desc } },
                onRemoveAll = { videoLinks = listOf(""); videoTitles = listOf(""); videoDescriptions = listOf() }
            )
            AudioSection(
                audioLinks = audioLinks,
                audioNames = audioNames,
                onLinkChange = { index, link -> audioLinks = audioLinks.toMutableList().apply { this[index] = link } },
                onNameChange = { index, name -> audioNames = audioNames.toMutableList().apply { this[index] = name } },
                onRemoveAll = { audioLinks = listOf(""); audioNames = listOf() }
            )
            Spacer(modifier = Modifier.height(100.dp))
        }
        CreateMaterialButton(
            context = context,
            viewModel = tambahMateriBelajarViewModel,
            imageUri = imageUri,
            judulMateri = judulMateri,
            deskripsiMateri = deskripsiMateri,
            videoLinks = videoLinks,
            videoTitles = videoTitles,
            videoDescriptions = videoDescriptions,
            audioLinks = audioLinks,
            audioNames = audioNames,
            modifier = Modifier.align(Alignment.BottomCenter),
            onBackClick = onBackClick
        )
        if (showSnackbar) {
            Snackbar(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                action = {
                    Button(onClick = { showSnackbar = false }) {
                        Text("OK")
                    }
                },
                content = {
                    Text(text = "File audio yang sama tidak dapat diunggah dua kali")
                }
            )
        }
    }

    // Observasi state dari ViewModel
    when (createdMaterialState) {
        is ResponseState.Loading -> {
            // Tampilkan loading indicator
            //LoadingScreen()
        }
        is ResponseState.Success -> {
            // Tampilkan sukses atau navigasi ke layar lain
            Toast.makeText(context, "Materi berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            onBackClick()
        }
        is ResponseState.Error -> {
            // Tampilkan pesan error
            Toast.makeText(context, "Error: ${(createdMaterialState as ResponseState.Error).error}", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun ImageSection(imageUri: Uri?, imageLauncher: ManagedActivityResultLauncher<String, Uri?>) {
    Column(modifier = Modifier.padding(16.dp)) {
        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } ?: run {
            Image(
                painter = painterResource(R.drawable.onboarding_image_third),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { imageLauncher.launch("image/*") }, colors = ButtonDefaults.buttonColors(containerColor = Blue)) {
            Text("Upload Gambar")
        }
    }
}

@Composable
fun TitleDescriptionSection(
    judulMateri: String,
    deskripsiMateri: String,
    onJudulChange: (String) -> Unit,
    onDeskripsiChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        InputTextFieldDefault(topText = "Judul Materi", insideText = "Masukan judul materi", valueText = judulMateri, onValueChange = onJudulChange)
        Spacer(modifier = Modifier.height(16.dp))
        InputTextFieldDefault(topText = "Deskripsi Materi", insideText = "Masukan deskripsi materi", valueText = deskripsiMateri, onValueChange = onDeskripsiChange)
    }
}

@Composable
fun VideoSection(
    videoLinks: List<String>,
    videoTitles: List<String>,
    videoDescriptions: List<String>,
    onLinkChange: (Int, String) -> Unit,
    onTitleChange: (Int, String) -> Unit,
    onDescriptionChange: (Int, String) -> Unit,
    onRemoveAll: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        videoLinks.forEachIndexed { index, link ->
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)) {
                InputTextFieldDefault(
                    topText = "Link Video ${index + 1}",
                    insideText = "Masukkan link video",
                    valueText = link,
                    onValueChange = { newLink -> onLinkChange(index, newLink) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                InputTextFieldDefault(
                    topText = "Judul Video ${index + 1}",
                    insideText = "Masukkan judul video",
                    valueText = videoTitles.getOrNull(index) ?: "",
                    onValueChange = { newTitle -> onTitleChange(index, newTitle) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                InputTextFieldDefault(
                    topText = "Deskripsi Video ${index + 1}",
                    insideText = "Masukkan deskripsi video",
                    valueText = videoDescriptions.getOrNull(index) ?: "",
                    onValueChange = { newDesc -> onDescriptionChange(index, newDesc) }
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { onLinkChange(videoLinks.size, "") }, colors = ButtonDefaults.buttonColors(containerColor = Blue)) {
                Text("Tambah Link Video")
            }
            if (videoLinks.isNotEmpty()) {
                Button(onClick = onRemoveAll, colors = ButtonDefaults.buttonColors(containerColor = AlertRed), modifier = Modifier.padding(vertical = 8.dp)) {
                    Text("Hapus Semua Link Video")
                }
            }
        }
    }
}

@Composable
fun AudioSection(
    audioLinks: List<String>,
    audioNames: List<String>,
    onLinkChange: (Int, String) -> Unit,
    onNameChange: (Int, String) -> Unit,
    onRemoveAll: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        audioLinks.forEachIndexed { index, link ->
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)) {
                InputTextFieldDefault(
                    topText = "Link Audio ${index + 1}",
                    insideText = "Masukkan link audio",
                    valueText = link,
                    onValueChange = { newLink -> onLinkChange(index, newLink) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                InputTextFieldDefault(
                    topText = "Nama File Audio ${index + 1}",
                    insideText = "Masukkan nama file audio",
                    valueText = audioNames.getOrNull(index) ?: "",
                    onValueChange = { newName -> onNameChange(index, newName) }
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { onLinkChange(audioLinks.size, "") }, colors = ButtonDefaults.buttonColors(containerColor = Blue)) {
                Text("Tambah Link Audio")
            }
            if (audioLinks.isNotEmpty()) {
                Button(onClick = onRemoveAll, colors = ButtonDefaults.buttonColors(containerColor = AlertRed), modifier = Modifier.padding(vertical = 8.dp)) {
                    Text("Hapus Semua Link Audio")
                }
            }
        }
    }
}

@Composable
fun CreateMaterialButton(
    modifier: Modifier,
    context: Context,
    viewModel: TambahMateriBelajarViewModel,
    imageUri: Uri?,
    judulMateri: String,
    deskripsiMateri: String,
    videoLinks: List<String>,
    videoTitles: List<String>,
    videoDescriptions: List<String>,
    audioLinks: List<String>,
    audioNames: List<String>,
    onBackClick: () -> Unit
) {
    Button(
        onClick = {

            val title = judulMateri.toRequestBody("text/plain".toMediaTypeOrNull())
            val description = deskripsiMateri.toRequestBody("text/plain".toMediaTypeOrNull())
            val category = "".toRequestBody("text/plain".toMediaTypeOrNull()) // Adjust as needed

            if (imageUri == null) {
                Toast.makeText(context, "Gambar materi harus diunggah", Toast.LENGTH_SHORT).show()
                return@Button
            }
            onBackClick()
            /*viewModel.createMaterial(
                image = imageUri!!,
                videoUrls = videoLinks,
                audioUrls = audioLinks,
                title = title,
                description = description,
                category = category
            )*/
        },
        modifier = modifier
            .padding(16.dp)
            .height(56.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Blue),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = "Tambah Materi")
    }
}







//OLD SCREEN
/*

//State
var materiVideoList by remember { mutableStateOf(listOf(MateriVideo("", "", ""))) }
var judulMateri by remember { mutableStateOf("") }
var deskripsiMateri by remember { mutableStateOf("") }
var imageUri by remember { mutableStateOf<Uri?>(null) }
var audioUris by remember { mutableStateOf(listOf<Uri>()) }
var audioNames by remember { mutableStateOf(listOf<String>()) }

var showSnackbar by remember { mutableStateOf(false) }

// Launcher for selecting an image
val imageLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent()
) { uri: Uri? ->
    imageUri = uri
}

// Launcher for selecting multiple audio files
val audioLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetMultipleContents()
) { uris: List<Uri> ->
    val newUris = uris.filterNot { audioUris.contains(it) }
    if (newUris.size < uris.size) {
        showSnackbar = true // Show snackbar if duplicate files are detected
    }
    audioUris = audioUris + newUris
    audioNames = audioNames + newUris.mapNotNull { uri ->
        uri.let {
            val documentFile = DocumentFile.fromSingleUri(context, it)
            documentFile?.name
        }
    }
    Log.d("audio", "audioLauncher: $audioNames")
}

Box(
modifier = Modifier
.fillMaxSize()
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            imageUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } ?: run {
                Image(
                    painter = painterResource(R.drawable.onboarding_image_third),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                imageLauncher.launch("image/*")
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                )) {
                Text("Upload Gambar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            InputTextFieldDefault(topText = "Judul Materi", insideText = "Masukan judul materi", valueText = judulMateri , onValueChange = { judulMateri = it})

            Spacer(modifier = Modifier.height(16.dp))

            InputTextFieldDefault(topText = "Deskripsi Materi", insideText = "Masukan deskripsi materi", valueText = deskripsiMateri , onValueChange = { deskripsiMateri = it})

            Spacer(modifier = Modifier.height(16.dp))

            //URL VIDEO
            materiVideoList.forEachIndexed { index, materiVideo ->
                InputTextFieldDefault(
                    topText = "Alamat Video Youtube ${index + 1}",
                    insideText = "Masukkan alamat video URL YouTube",
                    valueText = materiVideo.videoUrl,
                    onValueChange = { newUrl ->
                        materiVideoList = materiVideoList.toMutableList().apply {
                            this[index] = this[index].copy(videoUrl = newUrl)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                InputTextFieldDefault(
                    topText = "Judul Materi Video ${index + 1}",
                    insideText = "Masukkan judul materi video",
                    valueText = materiVideo.judul,
                    onValueChange = { newTitle ->
                        materiVideoList = materiVideoList.toMutableList().apply {
                            this[index] = this[index].copy(judul = newTitle)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                InputTextFieldDefault(
                    topText = "Deskripsi Materi Video ${index + 1}",
                    insideText = "Masukkan deskripsi materi video",
                    valueText = materiVideo.deskripsi,
                    onValueChange = { newDescription ->
                        materiVideoList = materiVideoList.toMutableList().apply {
                            this[index] = this[index].copy(deskripsi = newDescription)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (index == materiVideoList.lastIndex) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = {
                            materiVideoList = materiVideoList + MateriVideo("", "", "")
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Blue
                        )) {
                            Text("Tambah Materi Video")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        if (materiVideoList.size > 1) {
                            Button(onClick = {
                                materiVideoList = materiVideoList.dropLast(1)
                            }, colors = ButtonDefaults.buttonColors(
                                containerColor = AlertRed
                            )) {
                                Text("Hapus Materi Video Terakhir")
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            //AUDIO FILE

            audioUris.forEachIndexed { index, uri ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Materi Audio ${index + 1}: \n${audioNames.getOrNull(index) ?: uri}")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    audioLauncher.launch("audio/*")
                },colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                )) {
                    Text("Upload Audio")
                }

                if (audioUris.isNotEmpty()) {
                    Button(
                        onClick = {
                            audioUris = listOf()
                            audioNames = listOf()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AlertRed),
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text("Hapus Semua Materi Audio")
                    }
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }

    Button(
        onClick = { */
/*TODO*//*
 },
        modifier = Modifier
            .padding(16.dp)
            .height(56.dp)
            .align(Alignment.BottomCenter)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        //TODO LOGIC FOR ADD MATERI
        //TODO CHANGE TEXT TO ICON
        Text(text = "Tambah Materi")
    }

    if (showSnackbar) {
        Snackbar(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            action = {
                Button(onClick = { showSnackbar = false }) {
                    Text("OK")
                }
            },
            content = {
                Text(text = "File audio yang sama tidak dapat diunggah dua kali")
            }
        )
    }
}
*/

 */
        OTHERR SCREENNN
   @Composable
fun TambahMateriBelajarScreen(
    context: Context = LocalContext.current,
    onBackClick: () -> Unit
) {
    // ViewModel
    val tambahMateriBelajarViewModel: TambahMateriBelajarViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context.applicationContext)
    )

    // State dari ViewModel
    val createdMaterialState by tambahMateriBelajarViewModel.createdMaterial.collectAsState()
    val uploadVideoState by tambahMateriBelajarViewModel.uploadedVideoUrls.collectAsState()
    val uploadAudioState by tambahMateriBelajarViewModel.uploadedAudioUrls.collectAsState()

    var isUploadAllCompleted by remember { mutableStateOf(false) }

    // UI State
    var judulMateri by remember { mutableStateOf("") }
    var deskripsiMateri by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var videoUris by remember { mutableStateOf(listOf<Uri>()) }
    var videoTitles by remember { mutableStateOf(listOf("")) }
    var videoDescriptions by remember { mutableStateOf(listOf("")) }
    var audioUris by remember { mutableStateOf(listOf<Uri>()) }
    var audioNames by remember { mutableStateOf(listOf("")) }
    var showSnackbar by remember { mutableStateOf(false) }

    // Launchers for selecting media
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    val videoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        val newUris = uris.filterNot { videoUris.contains(it) }
        if (newUris.size < uris.size) {
            showSnackbar = true
        }
        videoUris = videoUris + newUris
        videoTitles = videoTitles + List(newUris.size) { "" }
        videoDescriptions = videoDescriptions + List(newUris.size) { "" }
    }

    val audioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        val newUris = uris.filterNot { audioUris.contains(it) }
        if (newUris.size < uris.size) {
            showSnackbar = true
        }
        audioUris = audioUris + newUris
        audioNames = audioNames + newUris.mapNotNull { uri ->
            uri.let {
                val documentFile = DocumentFile.fromSingleUri(context, it)
                documentFile?.name ?: ""
            }
        }
    }

    //LAUNCHED EFFECT
    LaunchedEffect(Unit) {
        tambahMateriBelajarViewModel.clearUploadedVideoUrls()
        tambahMateriBelajarViewModel.clearUploadedAudioUrls()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            ImageSection(imageUri, imageLauncher)
            TitleDescriptionSection(
                judulMateri = judulMateri,
                deskripsiMateri = deskripsiMateri,
                onJudulChange = { judulMateri = it },
                onDeskripsiChange = { deskripsiMateri = it }
            )
            VideoSection(
                videoUris = videoUris,
                videoTitles = videoTitles,
                videoDescriptions = videoDescriptions,
                videoLauncher = videoLauncher,
                onTitleChange = { index, title -> videoTitles = videoTitles.toMutableList().apply { this[index] = title } },
                onDescriptionChange = { index, desc -> videoDescriptions = videoDescriptions.toMutableList().apply { this[index] = desc } },
                onRemoveAll = { videoUris = listOf(); videoTitles = listOf(); videoDescriptions = listOf() }
            )
            AudioSection(
                audioUris = audioUris,
                audioNames = audioNames,
                audioLauncher = audioLauncher,
                onNameChange = { index, name -> audioNames = audioNames.toMutableList().apply { this[index] = name } },
                onRemoveAll = { audioUris = listOf(); audioNames = listOf() }
            )
            Spacer(modifier = Modifier.height(100.dp))
        }
        CreateMaterialButton(
            context = context,
            viewModel = tambahMateriBelajarViewModel,
            imageUri = imageUri,
            judulMateri = judulMateri,
            deskripsiMateri = deskripsiMateri,
            videoUris = videoUris,
            videoTitles = videoTitles,
            videoDescriptions = videoDescriptions,
            audioUris = audioUris,
            audioNames = audioNames,
            isUploadAllCompleted = isUploadAllCompleted,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        if (showSnackbar) {
            Snackbar(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                action = {
                    Button(onClick = { showSnackbar = false }) {
                        Text("OK")
                    }
                },
                content = {
                    Text(text = "File audio yang sama tidak dapat diunggah dua kali")
                }
            )
        }
    }

    // Observe the upload states and handle completion logic
    LaunchedEffect(uploadVideoState, uploadAudioState) {
        val allVideosUploaded = uploadVideoState is ResponseState.Success
        val allAudiosUploaded = uploadAudioState is ResponseState.Success

        isUploadAllCompleted = allVideosUploaded && allAudiosUploaded
    }

    // Observasi state dari ViewModel
    when (createdMaterialState) {
        is ResponseState.Loading -> {
            // Tampilkan loading indicator
            //LoadingScreen()
        }
        is ResponseState.Success -> {
            // Tampilkan sukses atau navigasi ke layar lain
            Toast.makeText(context, "Materi berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            onBackClick()
        }
        is ResponseState.Error -> {
            // Tampilkan pesan error
            Toast.makeText(context, "Error: ${(createdMaterialState as ResponseState.Error).error}", Toast.LENGTH_SHORT).show()
        }
    }
}



@Composable
fun ImageSection(imageUri: Uri?, imageLauncher: ManagedActivityResultLauncher<String, Uri?>) {
    Column(modifier = Modifier.padding(16.dp)) {
        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } ?: run {
            Image(
                painter = painterResource(R.drawable.onboarding_image_third),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { imageLauncher.launch("image/*") }, colors = ButtonDefaults.buttonColors(containerColor = Blue)) {
            Text("Upload Gambar")
        }
    }
}

@Composable
fun TitleDescriptionSection(
    judulMateri: String,
    deskripsiMateri: String,
    onJudulChange: (String) -> Unit,
    onDeskripsiChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        InputTextFieldDefault(topText = "Judul Materi", insideText = "Masukan judul materi", valueText = judulMateri, onValueChange = onJudulChange)
        Spacer(modifier = Modifier.height(16.dp))
        InputTextFieldDefault(topText = "Deskripsi Materi", insideText = "Masukan deskripsi materi", valueText = deskripsiMateri, onValueChange = onDeskripsiChange)
    }
}

@Composable
fun VideoSection(
    videoUris: List<Uri>,
    videoTitles: List<String>,
    videoDescriptions: List<String>,
    videoLauncher: ManagedActivityResultLauncher<String, List<Uri>>,
    onTitleChange: (Int, String) -> Unit,
    onDescriptionChange: (Int, String) -> Unit,
    onRemoveAll: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        videoUris.forEachIndexed { index, uri ->
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)) {
                Text(text = "Materi Video ${index + 1}: \n$uri")
                Spacer(modifier = Modifier.height(8.dp))
                InputTextFieldDefault(
                    topText = "Judul Video ${index + 1}",
                    insideText = "Masukkan judul video",
                    valueText = videoTitles.getOrNull(index) ?: "",
                    onValueChange = { newTitle -> onTitleChange(index, newTitle) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                InputTextFieldDefault(
                    topText = "Deskripsi Video ${index + 1}",
                    insideText = "Masukkan deskripsi video",
                    valueText = videoDescriptions.getOrNull(index) ?: "",
                    onValueChange = { newDesc -> onDescriptionChange(index, newDesc) }
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { videoLauncher.launch("video/*") }, colors = ButtonDefaults.buttonColors(containerColor = Blue)) {
                Text("Upload Video")
            }
            if (videoUris.isNotEmpty()) {
                Button(onClick = onRemoveAll, colors = ButtonDefaults.buttonColors(containerColor = AlertRed), modifier = Modifier.padding(vertical = 8.dp)) {
                    Text("Hapus Semua Materi Video")
                }
            }
        }
    }
}

@Composable
fun AudioSection(
    audioUris: List<Uri>,
    audioNames: List<String>,
    audioLauncher: ManagedActivityResultLauncher<String, List<Uri>>,
    onNameChange: (Int, String) -> Unit,
    onRemoveAll: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        audioUris.forEachIndexed { index, uri ->
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)) {
                Text(text = "Materi Audio ${index + 1}: \n${audioNames.getOrNull(index) ?: uri}")
                Spacer(modifier = Modifier.height(8.dp))
                InputTextFieldDefault(
                    topText = "Nama File Audio ${index + 1}",
                    insideText = "Masukkan nama file audio",
                    valueText = audioNames.getOrNull(index) ?: "",
                    onValueChange = { newName -> onNameChange(index, newName) }
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { audioLauncher.launch("audio/*") }, colors = ButtonDefaults.buttonColors(containerColor = Blue)) {
                Text("Upload Audio")
            }
            if (audioUris.isNotEmpty()) {
                Button(onClick = onRemoveAll, colors = ButtonDefaults.buttonColors(containerColor = AlertRed), modifier = Modifier.padding(vertical = 8.dp)) {
                    Text("Hapus Semua Materi Audio")
                }
            }
        }
    }
}
@Composable
fun CreateMaterialButton(
    modifier: Modifier,
    context: Context,
    viewModel: TambahMateriBelajarViewModel,
    imageUri: Uri?,
    judulMateri: String,
    deskripsiMateri: String,
    videoUris: List<Uri>,
    videoTitles: List<String>,
    videoDescriptions: List<String>,
    audioUris: List<Uri>,
    audioNames: List<String>,
    isUploadAllCompleted: Boolean
) {
    Button(
        onClick = {
            val imagePart = imageUri?.let { uri ->
                val file = getFileFromUri(context, uri)
                file?.let {
                    val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("image", it.name, requestFile)
                }
            }

            val title = judulMateri.toRequestBody("text/plain".toMediaTypeOrNull())
            val description = deskripsiMateri.toRequestBody("text/plain".toMediaTypeOrNull())
            val category = "".toRequestBody("text/plain".toMediaTypeOrNull()) // Adjust as needed

            // Upload video files
            videoUris.forEachIndexed { index, videoUri ->
                viewModel.uploadVideo(context, videoUri, videoTitles[index], videoDescriptions[index])
            }

            // Upload audio files
            audioUris.forEachIndexed { audioIndex, audioUri ->
                viewModel.uploadAudio(context, audioUri, audioNames[audioIndex])
            }

            if (isUploadAllCompleted) {
                viewModel.createMaterial(
                    image = imagePart,
                    videoUrls = videoUris.map { it.toString() },
                    audioUrls = audioUris.map { it.toString() },
                    title = title,
                    description = description,
                    category = category
                )
            } else {
                Toast.makeText(context, "Silahkan lakukan upload kembali", Toast.LENGTH_SHORT).show()
            }
        },
        modifier = modifier
            .padding(16.dp)
            .height(56.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Blue),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = "Tambah Materi")
    }
}


fun getFileFromUri(context: Context, contentUri: Uri): File? {
    val filePath = getRealPathFromURI(context, contentUri)
    return filePath?.let { File(it) }
}

fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
    var cursor: Cursor? = null
    return try {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        cursor = context.contentResolver.query(contentUri, proj, null, null, null)
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        cursor?.getString(columnIndex ?: 0)
    } finally {
        cursor?.close()
    }
}
 */