package com.lidm.facillify.ui.siswa.konsultasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lidm.facillify.R
import com.lidm.facillify.ui.chat.ChatList


data class Konsultant(
    var nama: String,
    var profesi: String,
    var profilImg: Int,
)

@Composable
fun KonsultasiSiswaScreen(
    modifier: Modifier,
    onNavigateToChat : () -> Unit
) {

    val data = listOf(
        Konsultant(
            "Dr. Ir. H. Fathul Muzakki, M. Sc.",
            "Psikolog",
            R.drawable.ic_launcher_background
        ),
        Konsultant(
            "Dr. Ir. H. Fathul Muzakki, M. Sc.",
            "Psikolog",
            R.drawable.ic_launcher_background
        ),
        Konsultant(
            "Dr. Ir. H. Fathul Muzakki, M. Sc.",
            "Psikolog",
            R.drawable.ic_launcher_background
        )
    )

    //        when (val response = viewModel.materiBelajar){
//            is Response.Loading -> {
//                /*TODO*/
//            }
//            is Response.Success -> {
//                /*TODO*/
//            }
//            is Response.Error -> {
//                /*TODO*/
//            }
//        }

    ChatList(
        data = data,
        modifier = modifier,
        onClick = onNavigateToChat
    )
}
