package com.lidm.facillify.data.local.paketMateri

import com.lidm.facillify.data.local.MateriBelajar
import com.lidm.facillify.data.local.VideoItem

val materi_bangun_ruang = MateriBelajar(
        id = 1,
        image = "https://asset-a.grid.id/crop/0x0:0x0/x/photo/2023/03/31/bangun-ruangjpg-20230331095251.jpg",
        title = "Bangun Ruang",
        desc = "Bab bangun ruang mencakup konsep-konsep dasar tentang berbagai bentuk tiga dimensi, seperti kubus, " +
                "balok, prisma, limas, tabung, kerucut, dan bola. Siswa akan mempelajari sifat-sifat dari masing-masing " +
                "bangun ruang, termasuk jumlah sisi, rusuk, dan titik sudut.",
        materiVideo = listOf(
            VideoItem(
                id = "eWBTTin_ZgE",
                title = "Bangun Ruang: Balok",
                thumbinal = "https://media.suara.com/pictures/653x366/2021/06/23/11192-ilustrasi-bangun-ruang-kerucut-kubus-balok-bola.jpg",
                desc = "Balok adalah salah satu bangun ruang tiga dimensi yang memiliki enam sisi berbentuk persegi panjang. " +
                        "Dalam materi ini, siswa akan mempelajari sifat-sifat dasar balok, seperti jumlah sisi, rusuk, dan titik sudut. " +
                        "Selain itu, mereka akan belajar cara menghitung luas permukaan dan volume balok menggunakan rumus yang tepat. ",
            ),

            VideoItem(
                id = "QjraET4O3nc",
                title = "Bangun Ruang: Kubus",
                thumbinal = "https://media.suara.com/pictures/653x366/2021/06/23/11192-ilustrasi-bangun-ruang-kerucut-kubus-balok-bola.jpg",
                desc = "Kubus adalah salah satu bangun ruang tiga dimensi yang memiliki enam sisi berbentuk persegi dengan ukuran yang sama. " +
                        "Dalam materi ini, siswa akan mempelajari sifat-sifat dasar kubus, termasuk jumlah sisi, rusuk, dan titik sudut. " +
                        "Siswa juga akan diajarkan cara menghitung luas permukaan dan volume kubus menggunakan rumus sederhana. ",
            ),

            VideoItem(
                id = "xvhltokwCMw",
                title = "Bangun Ruang: Limas ",
                thumbinal = "https://media.suara.com/pictures/653x366/2021/06/23/11192-ilustrasi-bangun-ruang-kerucut-kubus-balok-bola.jpg",
                desc = "Limas adalah bangun ruang tiga dimensi yang memiliki satu alas berbentuk segi banyak dan sisi-sisi lainnya berbentuk " +
                        "segitiga yang bertemu di satu titik puncak. Dalam materi ini, siswa akan mempelajari jenis-jenis limas berdasarkan " +
                        "bentuk alasnya, seperti limas segitiga, limas segiempat, dan seterusnya. Siswa akan memahami sifat-sifat dasar limas, " +
                        "termasuk jumlah sisi, rusuk, dan titik sudut. Selain itu, siswa akan diajarkan cara menghitung luas permukaan dan volume " +
                        "limas.",
            ),

            VideoItem(
                id = "20gSAnujBX0",
                title = "Bangun Ruang: Prisma",
                thumbinal = "https://media.suara.com/pictures/653x366/2021/06/23/11192-ilustrasi-bangun-ruang-kerucut-kubus-balok-bola.jpg",
                desc = "Limas adalah bangun ruang tiga dimensi yang memiliki satu alas berbentuk segi banyak dan sisi-sisi lainnya berbentuk " +
                        "segitiga yang bertemu di satu titik puncak. Dalam materi ini, siswa akan mempelajari jenis-jenis limas berdasarkan " +
                        "bentuk alasnya, seperti limas segitiga, limas segiempat, dan seterusnya. Siswa akan memahami sifat-sifat dasar limas, " +
                        "termasuk jumlah sisi, rusuk, dan titik sudut. Selain itu, siswa akan diajarkan cara menghitung luas permukaan dan volume " +
                        "limas.",
            ),
        )
)