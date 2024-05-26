package com.lidm.facillify.data.remote.request

data class RegisterRequest(
    val type: String,
    val email: String,
    val password: String,
    val name: String,
    val pob: String,
    val dob: String,
    val gender: String,
    val address: String,
    val phone_number: String,
    val religion: String,
    val nisn: String? = null,
    val nip : String? = null,
    val job : String? = null,
)
