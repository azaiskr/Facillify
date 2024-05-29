package com.lidm.facillify.data.local

data class HasilLatihan(
    val idLatihan : Int,
    var correctAnswer : Int,
    var grade: Int,
    var timeTaken: Int,
)

val hasilLatihan = mutableListOf<HasilLatihan>()


