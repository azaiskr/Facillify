package com.lidm.facillify.data.local

import com.lidm.facillify.data.local.paketLatihan.latihan_br_paket1
import com.lidm.facillify.data.local.paketLatihan.latihan_br_paket2

data class LatihanItem(
    val id: Int,
    val jmlSoal: Int,
    val judul: String,
    val deskripsi: String,
    val waktu: Int,
    val questions: List<Question>,
    val subBab: String,
    val done: Boolean = false,
    val answeKey: List<String>,
)

data class Question(
    val id: Int,
    val questionText: String,
    val answer: List<String>
)

val dataLatihan = listOf(
    latihan_br_paket1,
    latihan_br_paket2,
)