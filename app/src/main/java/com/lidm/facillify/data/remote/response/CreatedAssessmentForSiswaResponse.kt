package com.lidm.facillify.data.remote.response

data class CreatedAssessmentForSiswaResponse(
    val msg: String,
    val result: AssesmentResponse
)

data class AssesmentResponse(
    val email: String,
    val evaluation: String,
    val suggestion: String,
    val time: String,
)
