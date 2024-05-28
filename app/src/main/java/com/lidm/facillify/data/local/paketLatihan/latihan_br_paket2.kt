package com.lidm.facillify.data.local.paketLatihan

import com.lidm.facillify.data.local.LatihanItem
import com.lidm.facillify.data.local.Question

val latihan_br_paket2 = LatihanItem(
    id = 2,
    jmlSoal = 10,
    judul = "Bangun Ruang: Paket 2",
    deskripsi = "Latihan soal bab bangun ruang sisi datar paket 2",
    waktu = 30,
    questions = listOf(
        Question(
            id = 1,
            questionText = "Berapa banyak titik sudut pada limas segiempat?",
            answer = listOf("4", "3", "5", "8")
        ),
        Question(
            id = 2,
            questionText = "Berapa banyak rusuk pada prisma segitiga?",
            answer = listOf("9", "5", "12", "6")
        ),
        Question(
            id = 3,
            questionText = "Luas permukaan sebuah prisma segitiga adalah 408 cm² dengan tinggi prisma tersebut adalah 12 cm dan sisi miring segitiga 10 cm. Berapakah luas alas prisma segitiga tersebut?",
            answer = listOf("48 cm²", "24 cm²", "12 cm²", "36 cm²")
        ),
        Question(
            id = 4,
            questionText = "Volume limas segiempat sebesar 360 cm³ dengan panjang sisi alas 6 cm. Berapakah jarak titik puncak ke alas prisma tersebut?",
            answer = listOf("60 cm", "30 cm", "20 cm", "10 cm")
        ),
        Question(
            id = 5,
            questionText = "Balok memiliki volume 1.200 cm³. Panjang balok tersebut adalah 20 cm dan lebarnya 10 cm. Carilah tinggi balok tersebut dan jumlahkan seluruh panjang rusuk tinggi pada balok tersebut.",
            answer = listOf("24 cm", "12 cm", "36 cm", "40 cm")
        ),
        Question(
            id = 6,
            questionText = "Rima memiliki beras yang disimpan dalam dua wadah. Wadah-wadah tersebut berbentuk kubus dengan panjang rusuknya 20 cm. Berapa liter beras yang dimiliki Rima?",
            answer = listOf("8.000 liter", "60 liter", "8 liter", "600 liter")
        ),
        Question(
            id = 7,
            questionText = "Maikha akan membeli almari untuk kamarnya. Tinggi dinding kamar Maikha adalah 4 m. Sementara itu almari yang harus dibeli tidak boleh melebihi 3/4 tinggi dinding tersebut. Apabila almari yang akan dibeli Maikha berbentuk balok dengan luas permukaan sebesar 15,5 cm² dengan panjang 1,5 m dan lebar 1m. Apakah almari tersebut memenuhi syarat untuk dibeli Maikha?",
            answer = listOf(
                "Iya, karena tinggi almari kurang dari syarat.",
                "Iya. Karena tinggi almari sama dengan syarat minimum.",
                "Tidak, karena tinggi almari lebih dari syarat.",
                "Tidak, karena tinggi almari sama dengan syarat minimum."
            )
        ),
        Question(
            id = 8,
            questionText = "Ibu akan membuat kue berbentuk piramida dan akan disimpan ke dalam kotak berbentuk kubus dengan rusuk 20 cm. Apabila ibu membuat kue dengan volume 4.050 cm³ dan panjang alas piramida tersebut 15 cm. Apakah kue ibu dapat disimpan dalam kotak tersebut?",
            answer = listOf(
                "Iya, karena ukuran kue tidak melebihi ukuran kotak.",
                "Iya, karena ukuran kue sama dengan ukuran kotak.",
                "Tidak, karena ukuran kue sama dengan ukuran kotak.",
                "Tidak, karena ukuran kue melebihi kotak."
            )
        ),
        Question(
            id = 9,
            questionText = "Laila memiliki satu set teko dan gelas. Uniknya teko dan gelas tersebut berbentuk balok. Satu set berisi satu teko dan 5 gelas. Teko tersebut memiliki volume 3 liter dengan panjang 15 cm dan tingginya 20 cm. Lebar teko sama dengan lebar gelas. Kemudian panjang dan tinggi gelas 5 cm. Apabila teko tersebut terisi air penuh dan akan dituangkan ke 5 gelas tersebut. Berapa kali teko dapat mengisi kelima gelas tersebut dengan penuh?",
            answer = listOf(
                "1 kali tanpa sisa air di teko",
                "1 kali dengan sisa air di teko",
                "2 kali dengan sisa air di teko",
                "2 kali tanpa sisa air di teko"
            )
        ),
        Question(
            id = 10,
            questionText = "Shania membuat es batu. Ia membutuhkan sebanyak 100 buah. Shania sudah menyiapkan 10 cetakan dengan masing-masing cetakan terdapat 10 cetakan berbentuk kubus. Kubus-kubus tersebut memiliki panjang rusuk 3 cm. Apabila Shania menyiapkan air sebanyak 3 liter. Berapakah sisa air untuk membuat es batu?",
            answer = listOf(
                "1 liter",
                "0,2 liter",
                "0,03 liter",
                "0,3 liter"
            )
        )
    ),
    subBab = "Bangun Ruang",
    done = false,
    answeKey = listOf("C", "A", "B", "D", "A", "C", "A", "A", "C", "D")
)
