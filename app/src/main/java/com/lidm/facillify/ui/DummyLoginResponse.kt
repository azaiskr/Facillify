package com.lidm.facillify.ui

import com.lidm.facillify.util.Role

data class DummyLoginResponse(
    var username: String,
    val role: Role,
    var isTested: Boolean? = null
)
