package com.lidm.facillify.util

fun calculateLearningStyle(answers: List<String>): String {
    val countA = answers.count { it == "a" }
    val countB = answers.count { it == "b" }
    val countC = answers.count { it == "c" }
    val total = answers.size

    val percentA = (countA * 100) / total
    val percentB = (countB * 100) / total
    val percentC = (countC * 100) / total

    return when {
        percentA > percentB && percentA > percentC -> "Auditori"
        percentB > percentA && percentB > percentC -> "Kinestetik"
        percentC > percentA && percentC > percentB -> "Visual"
        percentA == percentB && percentA > percentC -> "Audio-Kinestetik"
        percentA == percentC && percentA > percentB -> "Audio-Visual"
        percentB == percentC && percentB > percentA -> "Kinestetik-Visual"
        else -> "Auditori-Kinestetik-Visual"
    }
}