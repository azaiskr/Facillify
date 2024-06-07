package com.lidm.facillify.data.remote.response

data class VIdeoInfoResponse (
    val msg: String,
    val result: ResultVideo
)

data class ResultVideo(
    val _id: String,
    val url: String,
    val title: String,
    val desc: String
)