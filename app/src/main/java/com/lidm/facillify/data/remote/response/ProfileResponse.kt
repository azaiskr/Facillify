package com.lidm.facillify.data.remote.response

data class ProfileResponse(
    val msg: String,
    val result: UserProfile
)

data class UserProfile(
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
    val profile_image_url: String? = null,
    val parent_email: String? = null,
    val nisn: String? = null,
    val job: String? = null,
    val nip: String? = null,
    val learning_style: String? = null
)

