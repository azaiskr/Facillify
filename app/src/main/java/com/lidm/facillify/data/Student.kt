package com.lidm.facillify.data

import androidx.annotation.DrawableRes
import com.lidm.facillify.R

data class Student(
    val name: String,
    val number: Long,
    @DrawableRes
    val image: Int,
)