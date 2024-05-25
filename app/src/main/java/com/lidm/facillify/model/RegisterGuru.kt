package com.lidm.facillify.model

data class RegisterGuru(
    val type: String,
    val email: String,
    val password: String,
    val name: String,
    val pob: String,
    val dob: String,
    val gender: String,
    val address: String,
    val phone_number: Float,
    val religion: String,
    val nip: Float
)
