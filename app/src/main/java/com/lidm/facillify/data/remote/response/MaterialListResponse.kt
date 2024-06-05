package com.lidm.facillify.data.remote.response

import com.google.gson.annotations.SerializedName
import com.lidm.facillify.data.local.MateriBelajar
import com.lidm.facillify.data.local.VideoItem

data class MaterialListResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("result")
	val result: List<MaterialList>? = emptyList()
)

data class MaterialList(

	@field:SerializedName("video_url")
	val videoUrl: String? = "",

	@field:SerializedName("music_list")
	val musicList: List<Any?>? = null,

	@field:SerializedName("description")
	val description: String? = "",

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("thumbnail_url")
	val thumbnailUrl: String? = "https://asset-a.grid.id/crop/0x0:0x0/x/photo/2023/03/31/bangun-ruangjpg-20230331095251.jpg",

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("category")
	val category: String? = "",

	@field:SerializedName("video_list")
	val videoList: List<Any?>? = null
)

fun convertMaterialListToMateriBelajar(
	materialList: MaterialList,
	localId: String
): MateriBelajar{
	val materiVideo = materialList.videoList?.map {
		VideoItem(
			id = it.toString(),
			title = materialList.title,
			thumbinal = materialList.thumbnailUrl ?: "",
			desc = materialList.description ?: "",
		)
	} ?: emptyList()

	val materiAudio = materialList.musicList?.map {
		VideoItem(
			id = it.toString(),
			title = materialList.title,
			thumbinal = materialList.thumbnailUrl ?: "",
			desc = materialList.description ?: "",
		)
	} ?: emptyList()

	return MateriBelajar(
		id = localId,
		image = materialList.thumbnailUrl ?: "",
		title = materialList.title,
		desc = materialList.description ?: "",
		materiVideo = materiVideo,
		materiAudio = materiAudio,
	)
}
