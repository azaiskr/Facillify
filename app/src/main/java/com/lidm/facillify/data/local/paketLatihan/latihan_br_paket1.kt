package com.lidm.facillify.data.local.paketLatihan

import com.lidm.facillify.data.local.LatihanItem
import com.lidm.facillify.data.local.Question

val latihan_br_paket1 = LatihanItem(
    id = 1,
    jmlSoal = 10,
    judul = "Bangun Ruang: Paket 1",
    deskripsi = "Latihan soal bab bangun ruang sisi datar paket 1",
    waktu = 30,
    questions = listOf(
        Question(
            id = 1,
            questionText = "Berapakah jumlah sisi bangun ruang kubus?",
            answer = listOf("3", "5", "6", "8")
        ),
        Question(
            id = 2,
            questionText = "Berbentuk apakah tegak prisma?",
            answer = listOf("Segiempat", "Lingkaran", "Segitiga", "Persegi panjang")
        ),
        Question(
            id = 3,
            questionText = "Sebuah kubus memiliki rusuk dengan panjang 5 cm, berapakah volumenya?",
            answer = listOf("125 cm³", "25 cm³", "115 cm³", "20 cm³")
        ),
        Question(
            id = 4,
            questionText = "Sebuah balok memiliki volume 60 cm³ dengan panjang sisi 5 cm dan lebarnya 4 cm. Berapakah tingginya?",
            answer = listOf("6 cm", "10 cm", "3 cm", "5 cm")
        ),
        Question(
            id = 5,
            questionText = "Sebuah limas segiempat memiliki tinggi 10 cm. Apabila volumenya 160 cm³. Berapakah panjang sisi alas limas segiempat tersebut?",
            answer = listOf("16 cm", "4 cm", "8 cm", "2 cm")
        ),
        Question(
            id = 6,
            questionText = "Prisma segitiga memiliki luas permukaan 60 cm². Sisi alas dan tutupnya berbentuk segitiga dengan alas segitiga 4 cm dan tinggi segitiga 3 cm. Berapakah luas salah satu sisi tegaknya?",
            answer = listOf("50 cm²", "45 cm²", "80 cm²", "60 cm²")
        ),
        Question(
            id = 7,
            questionText = "Sebuah rumah yang berbentuk seperti bangun ruang balok dengan limas segiempat di atasnya memiliki panjang dinding 10 m dengan lebar 6 m. Tinggi segitiga pada atap rumah adalah 4 m. Berapakah volume atap rumah tersebut?",
            answer = listOf("120 cm³", "240 cm³", "180 cm³", "60 cm³")
        ),
        Question(
            id = 8,
            questionText = "Terdapat dua buah bangun ruang yakni kubus dan limas dengan alas segiempat. Panjang rusuk kubus dengan panjang sisi alas limas adalah sama panjang. Volume kubus tersebut adalah 343 cm³. Tinggi limas tersebut adalah 10 cm. Berapakah volume limas tersebut?",
            answer = listOf("140 cm³", "70 cm³", "120 cm³", "490 cm³")
        ),
        Question(
            id = 9,
            questionText = "Sebuah kolam renang berbentuk balok. Tepi kolam renang tersebut diberi pot bunga dengan jarak antar pot 0,5 m. Banyaknya pot pada panjang kolam renang tersebut ada 7 pot dan pada lebar kolam renang ada 5 pot. Kolam renang tersebut juga diketahui memiliki kedalaman 4 m. Berapakah volume air pada kolam renang tersebut?",
            answer = listOf("10 cm³", "24 cm³", "140 cm³", "70 cm³")
        ),
        Question(
            id = 10,
            questionText = "Rani tersesat di hutan, perbekalannya sudah habis. Ia hanya memiliki 2 benda yakni sebuah botol dan wadah bekas berbentuk kubus. Setelah berjalan cukup lama ia menemukan sebuah sungai, akhirnya ia terpikir untuk mengisi botol tersebut dengan air menggunakan wadah bekas. Botol tersebut memiliki volume 1,5 liter. Sedangkan wadah bekas memiliki ukuran rusuk 10 cm. Berapa kali Rani harus mengisi botol tersebut menggunakan wadah?",
            answer = listOf(
                "2 kali dengan 1 kali wadah penuh air dan 1 kali setengah wadah terisi air.",
                "2 kali dengan kali wadah penuh air.",
                "1 kali dengan wadah terisi air setengah dari volume wadah.",
                "1 kali dengan wadah terisi air sebanyak 3/4 dari volume wadah."
            )
        )
    ),
    subBab = "Bangun Ruang",
    done = false,
    answeKey = listOf("C", "C", "A", "C", "B", "A", "D", "D", "B", "A")
)