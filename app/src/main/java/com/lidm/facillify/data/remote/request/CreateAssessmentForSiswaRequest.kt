package com.lidm.facillify.data.remote.request

data class CreateAssessmentForSiswaRequest(
    val email: String,
    val evaluation: String,
    val suggestion: String,
)
