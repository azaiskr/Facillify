package com.lidm.facillify.ui.guru.materibelajar

import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import coil.compose.rememberAsyncImagePainter
import com.lidm.facillify.R
import com.lidm.facillify.model.MateriVideo
import com.lidm.facillify.ui.components.InputTextFieldDefault
import com.lidm.facillify.ui.theme.AlertRed
import com.lidm.facillify.ui.theme.Blue

@Composable
fun TambahMateriBelajarScreen() {
    //State
    var materiVideoList by remember { mutableStateOf(listOf(MateriVideo("", "", ""))) }
    var judulMateri by remember { mutableStateOf("") }
    var deskripsiMateri by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var audioUris by remember { mutableStateOf(listOf<Uri>()) }
    var audioNames by remember { mutableStateOf(listOf<String>()) }

    val context = LocalContext.current
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
            onClick = { /*TODO*/ },
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
}

@Composable
@Preview(showBackground = true)
fun TambahMateriBelajarScreenPreview() {
    TambahMateriBelajarScreen()
}