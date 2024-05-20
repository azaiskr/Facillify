package com.lidm.facillify.data

data class LatihanSoal(
    val JudulLatihanSoal: String,
    val DeskripsiLatihanSoal: String,
    val waktuLatihanSoal: Int,
    val Soal: List<ItemSoal>
)

data class ItemSoal(
    val soal: String,
    val jawaban: List<String>,
    val jawabanBenar: String
)
