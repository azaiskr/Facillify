package com.lidm.facillify.data.remote.response

data class GetAllStudentResponse(
    val msg: String,
    val result: List<GetStudentResponse>
)

data class GetStudentResponse(
    val _id: String,
    val type: String,
    val email: String,
    val name: String,
    val pob: String,
    val dob: String,
    val gender: String,
    val address: String,
    val phone_number: String,
    val religion: String,
    val nisn: String,
    val profile_image_url: String,
    val parent_email: String
)