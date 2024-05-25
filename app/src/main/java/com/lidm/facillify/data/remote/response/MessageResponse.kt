package com.lidm.facillify.data.remote.response

import com.google.gson.annotations.SerializedName

data class MessageResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("_id_created")
	val idCreated: String? = null
)
