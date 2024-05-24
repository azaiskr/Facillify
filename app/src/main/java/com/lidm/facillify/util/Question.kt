package com.lidm.facillify.util

data class Question(
    val id: Int,
    val questionText: String,
    val answer: List<String>
)

val dummyQuestions = listOf(
    Question(
        id = 1,
        questionText = "Pada video ini kita akan mempelajari mengenai bangun ruang yang ada di sekitar kita",
        answer = listOf("Video 1", "Video 2", "Video 3")
    ),
    Question(
        id = 2,
        questionText = "Soal nomer dua ",
        answer = listOf("Pilihan 1", "Pilihan 2", "Pilihan 3")
    ),
    Question(
        id = 3,
        questionText = "Soal nomer tiga ",
        answer = listOf("Pilihan 1", "Pilihan 2", "Pilihan 3")
    ),
    Question(
        id = 4,
        questionText = "Soal nomer empat ",
        answer = listOf("Pilihan 1", "Pilihan 2", "Pilihan 3")
    ),
)
