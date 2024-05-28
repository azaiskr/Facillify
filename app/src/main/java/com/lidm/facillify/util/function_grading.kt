package com.lidm.facillify.util

import com.lidm.facillify.data.local.QuizResult

fun getGrade(answer: MutableList<String>, keys: List<String>): QuizResult {
    val correctAnswer = mutableListOf<Int>()
    val wrongAnswer = mutableListOf<Int>()
    val totalAnswer = answer.size

    answer.forEachIndexed { index, ans ->
        if (ans == keys[index]) {
            correctAnswer.add(index)
        } else {
            wrongAnswer.add(index)
        }
    }

    val grade =  (correctAnswer.size.toDouble() / totalAnswer)*100.0
    return QuizResult(grade.toInt(), correctAnswer.size)
}

