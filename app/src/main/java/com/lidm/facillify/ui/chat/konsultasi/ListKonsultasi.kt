package com.lidm.facillify.ui.chat.konsultasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lidm.facillify.R
import com.lidm.facillify.ui.chat.ChatList
import com.lidm.facillify.ui.konsultasi.KonsultasiForumScreen


data class Konsultant(
    var nama: String,
    var profesi: String,
    var profilImg: Int,
)
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

@Composable
fun ListKonsultasi(
    modifier: Modifier,
    onNavigateToChat : () -> Unit
) {

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

    KonsultasiForumScreen(
        onClickChat = onNavigateToChat
    )
}
