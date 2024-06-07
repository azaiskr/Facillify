package com.lidm.facillify.data.remote.response

data class MaterialResponse(
    val thumbnail_url: String,
    val video_url: String,
    val title: String,
    val description: String,
    val category: String,
    val music_list: List<String>,
    val video_list: List<String>,
    val _id: String
)