package com.lidm.facillify.data.local

data class Filter(
    val id: Int,
    val name: String
)

val dummyDataFilter = listOf(
    Filter(
        id = 1,
        name = "Selesai"
    ),
    Filter(
        id = 2,
        name = "Bangun Ruang"
    ),
    Filter(
        id = 3,
        name = "Statistika"
    ),
    Filter(
        id = 4,
        name = "SPLDV"
    ),
)