package com.lidm.facillify.data.remote.response

data class AudioInfoResponse(
    val msg: String,
    val result: ResultAudio
)

data class ResultAudio(
    val _id: String,
    val url: String,
    val title: String
)