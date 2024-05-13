package com.lidm.facillify.util

import androidx.annotation.DrawableRes
import com.lidm.facillify.R

sealed class OnboardingPage(
    @DrawableRes
    val image: Int,
    val tittle: String,
    val description: String,
) {
    data object PageOne : OnboardingPage(
        image = R.drawable.onboarding_image_one,
        tittle = "Penyesuaian Gaya Belajar",
        description = "Dengan penyesuaian metode gaya belajar dengan menjawab pertanyaan tes, murid akan lebih mudah dalam memahami pelajaran."
    )

    data object PageTwo : OnboardingPage(
        image = R.drawable.onboarding_image_second,
        tittle = "Sistem Pelajaran yang terintegrasi",
        description = "Guru dapat mengunggah materi pengajaran kepada murid secara real-time. Guru juga dapat memberi saran dan masukan dalam bentuk chat dengan murid."
    )

    data object PageThree : OnboardingPage(
        image = R.drawable.onboarding_image_third,
        tittle = "Tracker",
        description = "Orang tua dapat melihat perkembangan belajar anak serta masukan dari guru secara langsung."
    )
}