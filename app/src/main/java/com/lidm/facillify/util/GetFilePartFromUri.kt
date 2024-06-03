package com.lidm.facillify.util

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

fun getFilePartFromUri(context: Context, uri: Uri, partName: String): MultipartBody.Part? {
    val contentResolver = context.contentResolver
    val type = contentResolver.getType(uri) ?: "image/jpeg" // Default to JPEG if type is not available
    val file = File.createTempFile("temp", ".jpg", context.cacheDir) // Ensure the extension is .jpg
    val inputStream = contentResolver.openInputStream(uri) ?: return null
    val outputStream = FileOutputStream(file)
    inputStream.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }
    val requestBody = file.asRequestBody(type.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(partName, file.name, requestBody)
}
