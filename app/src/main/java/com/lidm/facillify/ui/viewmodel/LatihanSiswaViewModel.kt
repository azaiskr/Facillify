package com.lidm.facillify.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lidm.facillify.data.local.HasilLatihan
import com.lidm.facillify.data.local.QuizResult

class LatihanSiswaViewModel : ViewModel() {

    private val _quizResult = MutableLiveData<HasilLatihan>()
    val quizResult: LiveData<HasilLatihan> = _quizResult

    fun getGrade(
        latihanId: Int,
        answer: MutableList<String>,
        keys: List<String>,
        timetaken: Int,
    ): HasilLatihan {
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

        val grade = (correctAnswer.size.toDouble() / totalAnswer) * 100.0
        return HasilLatihan(
            idLatihan = latihanId,
            grade = grade.toInt(),
            timeTaken = timetaken,
            correctAnswer = correctAnswer.size,
        )
    }
}