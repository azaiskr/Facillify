package com.lidm.facillify.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.lidm.facillify.R
import okhttp3.OkHttpClient

@Composable
fun DynamicSizeImage(
    imageUrl: String,
    bearerToken: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .okHttpClient {
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $bearerToken")
                        .build()
                    chain.proceed(request)
                }
                .build()
        }
        .build()

    var loadError by remember { mutableStateOf(false) }

    if (imageUrl.isEmpty() || imageUrl.isBlank() || loadError == true) {
        Image(
            modifier = modifier,
            painter = painterResource(id = R.drawable.pp_deafult),
            contentDescription = "Dynamic Size Image")
    } else {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .size(300, 300)
                .transformations(CircleCropTransformation())
                .diskCachePolicy(CachePolicy.ENABLED)
                .networkCachePolicy(CachePolicy.ENABLED)
                .listener(
                    onStart = { request ->
                        Log.d("CoilDebug", "Start loading image: ${request.data}")
                        loadError = false
                    },
                    onSuccess = { request, metadata ->
                        Log.d("CoilDebug", "Successfully loaded image: ${request.data}")
                        loadError = false
                    },
                    onError = { request, error ->
                        Log.e("CoilDebug", "Error loading image: ${request.data}", error.throwable)
                        loadError = true
                    }
                )
                .build(),
            contentDescription = "Dynamic Size Image",
            imageLoader = imageLoader,
            modifier = modifier,
            contentScale = contentScale
        )
    }
}
