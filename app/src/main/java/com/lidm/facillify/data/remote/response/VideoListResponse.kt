package com.lidm.facillify.data.remote.response

import com.google.gson.annotations.SerializedName

data class VideoListResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("result")
	val result: List<ResultItem?>? = null
)

data class ResultItem(

	@field:SerializedName("video_url")
	val videoUrl: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("thumbnail_url")
	val thumbnailUrl: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("category")
	val category: String
)
