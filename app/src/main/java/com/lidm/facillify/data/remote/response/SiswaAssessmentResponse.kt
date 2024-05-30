package com.lidm.facillify.data.remote.response

data class SiswaAssessmentResponse(
    val msg: String,
    val result: DetailAssesment
)

data class DetailAssesment(
    val _id: String,
    val email: String,
    val evaluation: String,
    val suggestion: String,
    val time: String,
)
